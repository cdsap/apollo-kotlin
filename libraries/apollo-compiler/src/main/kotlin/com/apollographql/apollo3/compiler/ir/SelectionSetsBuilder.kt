package com.apollographql.apollo3.compiler.ir

import com.apollographql.apollo3.ast.GQLArgument
import com.apollographql.apollo3.ast.GQLField
import com.apollographql.apollo3.ast.GQLFragmentDefinition
import com.apollographql.apollo3.ast.GQLFragmentSpread
import com.apollographql.apollo3.ast.GQLInlineFragment
import com.apollographql.apollo3.ast.GQLListType
import com.apollographql.apollo3.ast.GQLNamedType
import com.apollographql.apollo3.ast.GQLNonNullType
import com.apollographql.apollo3.ast.GQLSelection
import com.apollographql.apollo3.ast.GQLType
import com.apollographql.apollo3.ast.Schema
import com.apollographql.apollo3.ast.definitionFromScope
import com.apollographql.apollo3.ast.rawType
import com.apollographql.apollo3.compiler.capitalizeFirstLetter
import com.apollographql.apollo3.compiler.codegen.keyArgs
import com.apollographql.apollo3.compiler.codegen.paginationArgs

internal class SelectionSetsBuilder(
    val schema: Schema,
    private val allFragmentDefinitions: Map<String, GQLFragmentDefinition>,
) {
  private val usedNames = mutableSetOf("root")

  private fun resolveNameClashes(usedNames: MutableSet<String>, modelName: String): String {
    var i = 0
    var name = modelName
    while (usedNames.contains(name)) {
      i++
      name = "$modelName$i"
    }
    usedNames.add(name)
    return name
  }

  fun build(gqlSelections: List<GQLSelection>, parentType: String): List<IrSelectionSet> {
    return gqlSelections.walk("root", true, parentType)
  }

  private class WalkResult(val self: IrSelection, val nested: List<IrSelectionSet>)

  /**
   * @param name the name to use for this [IrSelectionSet]. Must be unique for a given operation/fragment definition
   */
  private fun List<GQLSelection>.walk(name: String, isRoot: Boolean, parentType: String): List<IrSelectionSet> {
    val results = mapNotNull { it.walk(parentType) }

    /**
     * Order is important. The selection set will ultimately reference nested selection sets, so the nested need to come first
     * to avoid the Kotlin compiler giving an error
     */
    return results.flatMap { it.nested } + IrSelectionSet(name, isRoot, results.map { it.self })
  }

  private fun GQLSelection.walk(parentType: String): WalkResult? {
    return when (this) {
      is GQLField -> walk(parentType)
      is GQLInlineFragment -> walk(parentType)
      is GQLFragmentSpread -> walk()
    }
  }

  private fun GQLArgument.toIr(keyArgs: Set<String>, paginationArgs: Set<String>): IrArgument {
    return IrArgument(
        name = name,
        value = value.toIrValue(),
        isKey = keyArgs.contains(name),
        isPagination = paginationArgs.contains(name)
    )
  }

  private fun GQLField.walk(parentType: String): WalkResult? {
    val expression = directives.toIncludeBooleanExpression()
    if (expression == BooleanExpression.False) {
      return null
    }

    val fieldDefinition = definitionFromScope(schema, parentType)!!

    val selectionSetName = resolveNameClashes(usedNames, name)
    return WalkResult(
        self = IrField(
            name = name,
            alias = alias,
            type = fieldDefinition.type.toIrTypeRef(),
            arguments = arguments.let { gqlArguments ->
              val typeDefinition = schema.typeDefinition(parentType)
              val keyArgs = typeDefinition.keyArgs(name, schema)
              val paginationArgs = typeDefinition.paginationArgs(name, schema)
              gqlArguments.map { gqlArgument ->
                gqlArgument.toIr(keyArgs, paginationArgs)
              }
            },
            condition = expression,
            selectionSetName = if (selections.isNotEmpty()) selectionSetName else null
        ),
        nested = if (selections.isEmpty()) emptyList() else selections.walk(selectionSetName, false, fieldDefinition.type.rawType().name)
    )
  }

  /**
   * This doesn't register the used types like [IrOperationsBuilder.toIr] but since it's going through the same AST, that's not a bad thing
   */
  private fun GQLType.toIrTypeRef(): IrTypeRef = when(this) {
    is GQLNonNullType -> IrNonNullTypeRef(this.type.toIrTypeRef())
    is GQLListType -> IrListTypeRef(type.toIrTypeRef())
    is GQLNamedType -> IrNamedTypeRef(name)
  }

  private fun GQLInlineFragment.walk(parentType: String): WalkResult? {
    val expression = directives.toIncludeBooleanExpression()
    if (expression == BooleanExpression.False) {
      return null
    }

    val tc = typeCondition?.name ?: parentType
    val name = "on${tc.capitalizeFirstLetter()}"
    val selectionSetName = resolveNameClashes(usedNames, name)
    return WalkResult(
        self = IrFragment(
            typeCondition = tc,
            // TODO: restrict the possible types to the possible types in the context of this selection
            possibleTypes = schema.possibleTypes(tc).toList(),
            condition = expression,
            selectionSetName = selectionSetName,
            name = null
        ),
        nested = selections.walk(selectionSetName, false, tc)
    )
  }

  private fun GQLFragmentSpread.walk(): WalkResult? {
    val expression = directives.toIncludeBooleanExpression()
    if (expression == BooleanExpression.False) {
      return null
    }

    val fragmentDefinition = allFragmentDefinitions[name]!!

    return WalkResult(
        self = IrFragment(
            typeCondition = fragmentDefinition.typeCondition.name,
            // TODO: restrict the possible types to the possible types in the context of this selection
            possibleTypes = schema.possibleTypes(fragmentDefinition.typeCondition.name).toList(),
            condition = expression,
            selectionSetName = null,
            name = name
        ),
        nested = emptyList()
    )
  }
}
