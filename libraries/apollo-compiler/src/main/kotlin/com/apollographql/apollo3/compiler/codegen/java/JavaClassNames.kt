package com.apollographql.apollo3.compiler.codegen.java

import com.apollographql.apollo3.compiler.codegen.ClassNames
import com.apollographql.apollo3.compiler.codegen.ClassNames.apolloApiJsonPackageName
import com.apollographql.apollo3.compiler.codegen.ClassNames.apolloApiPackageName
import com.apollographql.apollo3.compiler.codegen.ResolverClassName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName

/**
 * A list of constant [ResolverClassName] that don't use `class.name` and therefore survive proguard/R8
 */
internal object JavaClassNames {
  val ObjectType = ClassNames.ObjectType.toJavaPoetClassName()
  val InterfaceType = ClassNames.InterfaceType.toJavaPoetClassName()
  val ObjectTypeBuilder = ClassNames.ObjectTypeBuilder.toJavaPoetClassName()
  val InterfaceTypeBuilder = ClassNames.InterfaceTypeBuilder.toJavaPoetClassName()

  val JsonReader = ClassNames.JsonReader.toJavaPoetClassName()
  val JsonWriter = ClassNames.JsonWriter.toJavaPoetClassName()
  val CustomScalarAdapters = ClassNames.CustomScalarAdapters.toJavaPoetClassName()
  val Input = ClassNames.Input.toJavaPoetClassName()
  val CustomScalarAdaptersBuilder = ClassNames.CustomScalarAdaptersBuilder.toJavaPoetClassName()
  val Optional = ClassNames.Optional.toJavaPoetClassName()
  val Absent = ClassNames.Absent.toJavaPoetClassName()
  val Present = ClassNames.Present.toJavaPoetClassName()
  val CompositeAdapter = ClassNames.CompositeAdapter.toJavaPoetClassName()
  val Adapter = ClassNames.Adapter.toJavaPoetClassName()
  val CompositeAdapterContext = ClassNames.CompositeAdapterContext.toJavaPoetClassName()
  val CompiledSelection = ClassNames.CompiledSelection.toJavaPoetClassName()
  val CompiledNamedType = ClassNames.CompiledNamedType.toJavaPoetClassName()
  val UnionType = ClassNames.UnionType.toJavaPoetClassName()
  val Fragment = ClassNames.Fragment.toJavaPoetClassName()
  val FragmentData = ClassNames.FragmentData.toJavaPoetClassName()
  val Query = ClassNames.Query.toJavaPoetClassName()
  val Mutation = ClassNames.Mutation.toJavaPoetClassName()
  val Subscription = ClassNames.Subscription.toJavaPoetClassName()
  val QueryData = ClassNames.QueryData.toJavaPoetClassName()
  val MutationData = ClassNames.MutationData.toJavaPoetClassName()
  val SubscriptionData = ClassNames.SubscriptionData.toJavaPoetClassName()
  val EnumType = ClassNames.EnumType.toJavaPoetClassName()
  val CustomScalarType = ClassNames.CustomScalarType.toJavaPoetClassName()
  val True = ClassNames.True.toJavaPoetClassName()
  val False = ClassNames.False.toJavaPoetClassName()
  val CompiledArgument = ClassNames.CompiledArgument.toJavaPoetClassName()
  val CompiledVariable = ClassNames.CompiledVariable.toJavaPoetClassName()
  val CompiledCondition = ClassNames.CompiledCondition.toJavaPoetClassName()
  val CompiledField = ClassNames.CompiledField.toJavaPoetClassName()
  val CompiledFieldBuilder = ClassNames.CompiledFieldBuilder.toJavaPoetClassName()
  val CompiledFragment = ClassNames.CompiledFragment.toJavaPoetClassName()
  val CompiledFragmentBuilder = ClassNames.CompiledFragmentBuilder.toJavaPoetClassName()
  val FakeResolver = ClassNames.FakeResolver.toJavaPoetClassName()
  val FakeResolverKt = ClassName.get(apolloApiPackageName, "FakeResolverKt")
  val DefaultFakeResolver = ClassNames.DefaultFakeResolver.toJavaPoetClassName()

  val Builder = ClassName.get("", "Builder")

  /**
   * ClassNames that we don't use in Kotlin because we use extension functions instead
   */
  val CompiledNotNullType = ClassName.get(apolloApiPackageName, "CompiledNotNullType")
  val CompiledListType = ClassName.get(apolloApiPackageName, "CompiledListType")
  val ObjectCompositeAdapter = ClassName.get(apolloApiPackageName, "ObjectCompositeAdapter")
  val And = ClassName.get(apolloApiPackageName, "BooleanExpression", "And")
  val Or = ClassName.get(apolloApiPackageName, "BooleanExpression", "Or")
  val Not = ClassName.get(apolloApiPackageName, "BooleanExpression", "Not")
  val BooleanExpressionElement = ClassName.get(apolloApiPackageName, "BooleanExpression", "Element")
  val BTerm = ClassName.get(apolloApiPackageName, "BTerm")
  val BVariable = ClassName.get(apolloApiPackageName, "BVariable")
  val BPossibleTypes = ClassName.get(apolloApiPackageName, "BPossibleTypes")
  val BLabel = ClassName.get(apolloApiPackageName, "BLabel")
  val ImmutableMapBuilder = ClassName.get(apolloApiPackageName, "ImmutableMapBuilder")
  val NullableAdapter = ClassName.get(apolloApiPackageName, "NullableAdapter")
  val NullableCompositeAdapter = ClassName.get(apolloApiPackageName, "NullableCompositeAdapter")
  val ListAdapter = ClassName.get(apolloApiPackageName, "ListAdapter")
  val ListCompositeAdapter = ClassName.get(apolloApiPackageName, "ListCompositeAdapter")
  val ApolloOptionalAdapter = ClassName.get(apolloApiPackageName, "ApolloOptionalAdapter")
  val ApolloOptionalCompositeAdapter = ClassName.get(apolloApiPackageName, "ApolloOptionalCompositeAdapter")

  val IOException = ClassName.get("java.io", "IOException")

  /**
   * ClassNames for kotlin files turned into java classes
   */
  val Adapters = ClassName.get(apolloApiPackageName, "Adapters")
  val JavaOptionalAdapter = ClassName.get("com.apollographql.apollo3.api.java.adapter", "JavaOptionalAdapter")
  val JavaOptionalCompositeAdapter = ClassName.get("com.apollographql.apollo3.api.java.adapter", "JavaOptionalCompositeAdapter")
  val JavaOptionalAdapters = ClassName.get("com.apollographql.apollo3.api.java.adapter", "JavaOptionalAdapters")
  val GuavaOptionalAdapter = ClassName.get("com.apollographql.apollo3.api.java.adapter", "GuavaOptionalAdapter")
  val GuavaOptionalCompositeAdapter = ClassName.get("com.apollographql.apollo3.api.java.adapter", "GuavaOptionalCompositeAdapter")
  val GuavaOptionalAdapters = ClassName.get("com.apollographql.apollo3.api.java.adapter", "GuavaOptionalAdapters")
  val BooleanExpressions = ClassName.get(apolloApiPackageName, "BooleanExpressions")
  val Assertions = ClassName.get(apolloApiPackageName, "Assertions")
  val JsonReaders = ClassName.get(apolloApiJsonPackageName, "JsonReaders")
  val PossibleTypes = ClassName.get(apolloApiPackageName, "PossibleTypes")

  /**
   * ClassNames for builtin Java types
   */
  val String: ClassName = ClassName.get("java.lang", "String")
  val Integer: ClassName = ClassName.get("java.lang", "Integer")
  val Double: ClassName = ClassName.get("java.lang", "Double")
  val Object: ClassName = ClassName.get("java.lang", "Object")
  val Boolean: ClassName = ClassName.get("java.lang", "Boolean")
  val Deprecated: ClassName = ClassName.get("java.lang", "Deprecated")
  val Override: ClassName = ClassName.get("java.lang", "Override")
  val SuppressWarnings: ClassName = ClassName.get("java.lang", "SuppressWarnings")

  val List: ClassName = ClassName.get("java.util", "List")
  val ArrayList: ClassName = ClassName.get("java.util", "ArrayList")
  val Arrays = ClassName.get("java.util", "Arrays")
  val Collections = ClassName.get("java.util", "Collections")
  val IllegalStateException = ClassName.get("java.lang", "IllegalStateException")
  val HashMap = ClassName.get("java.util", "HashMap")
  val Map: ClassName = ClassName.get("java.util", "Map")
  val MapOfStringToObject = ParameterizedTypeName.get(Map, String, Object)
  val JavaOptional = ClassName.get("java.util", "Optional")

  val ObjectBuilderKt = ClassName.get(apolloApiPackageName, "ObjectBuilderKt")
  val ObjectMap = ClassName.get(apolloApiPackageName, "ObjectMap")

  val JetBrainsNullable = ClassNames.JetBrainsNullable.toJavaPoetClassName()
  val JetBrainsNonNull = ClassNames.JetBrainsNonNull.toJavaPoetClassName()
  val AndroidNullable = ClassName.get("androidx.annotation", "Nullable")
  val AndroidNonNull = ClassName.get("androidx.annotation", "NonNull")
  val Jsr305Nullable = ClassName.get("javax.annotation", "Nullable")
  val Jsr305NonNull = ClassName.get("javax.annotation", "Nonnull")

  val GuavaOptional = ClassName.get("com.google.common.base", "Optional")
}
