@file:Suppress("FunctionName")

package base.lithocoreresources.domain.dsl

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.Card
import com.facebook.litho.widget.CardClip
import com.facebook.litho.widget.EditText
import com.facebook.litho.widget.EmptyComponent
import com.facebook.litho.widget.HorizontalScroll
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Progress
import com.facebook.litho.widget.Recycler
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalScroll

fun <B : Component.Builder<B>> ComponentContext.component(
        create: (c: ComponentContext) -> B,
        block: B.() -> Unit
): Component {
    val builder = create(this)
    builder.block()
    return builder.build()
}

fun <B : Component.Builder<B>> ComponentContext.componentBuilder(
        create: (c: ComponentContext) -> B,
        block: B.() -> Unit
): Component.Builder<B> {
    val builder = create(this)
    builder.block()
    return builder
}

fun <B : Component.ContainerBuilder<B>> Component.ContainerBuilder<B>.Children(block: ComponentContainer.() -> Unit) {
    val componentContainer = ComponentContainer(requireNotNull(context))
    componentContainer.block()
    componentContainer.addChildren(this)
}

fun Card(c: ComponentContext, block: Card.Builder.() -> Unit): Component.Builder<Card.Builder> {
    return c.componentBuilder(Card::create, block)
}

fun CardClip(c: ComponentContext, block: CardClip.Builder.() -> Unit): Component.Builder<CardClip.Builder> {
    return c.componentBuilder(CardClip::create, block)
}

fun Column(c: ComponentContext, block: Column.Builder.() -> Unit): Component {
    return c.component(Column::create, block)
}

fun EditText(c: ComponentContext, block: EditText.Builder.() -> Unit): Component.Builder<EditText.Builder> {
    return c.componentBuilder(EditText::create, block)
}

fun Empty(c: ComponentContext, block: EmptyComponent.Builder.() -> Unit): Component.Builder<EmptyComponent.Builder> {
    return c.componentBuilder(EmptyComponent::create, block)
}

fun HorizontalScroll(
        c: ComponentContext,
        block: HorizontalScroll.Builder.() -> Unit
): Component.Builder<HorizontalScroll.Builder> {
    return c.componentBuilder(HorizontalScroll::create, block)
}

fun Image(c: ComponentContext, block: Image.Builder.() -> Unit): Component.Builder<Image.Builder> {
    return c.componentBuilder(Image::create, block)
}

fun Progress(c: ComponentContext, block: Progress.Builder.() -> Unit): Component.Builder<Progress.Builder> {
    return c.componentBuilder(Progress::create, block)
}

fun Recycler(c: ComponentContext, block: Recycler.Builder.() -> Unit): Component.Builder<Recycler.Builder> {
    return c.componentBuilder(Recycler::create, block)
}

fun RecyclerCollectionComponent(
        c: ComponentContext,
        block: RecyclerCollectionComponent.Builder.() -> Unit
): Component.Builder<RecyclerCollectionComponent.Builder> {
    return c.componentBuilder(RecyclerCollectionComponent::create, block)
}

fun Row(c: ComponentContext, block: Row.Builder.() -> Unit): Component {
    return c.component(Row::create, block)
}

fun SolidColor(c: ComponentContext, block: SolidColor.Builder.() -> Unit): Component.Builder<SolidColor.Builder> {
    return c.componentBuilder(SolidColor::create, block)
}

fun Text(c: ComponentContext, block: Text.Builder.() -> Unit): Component.Builder<Text.Builder> {
    return c.componentBuilder(Text::create, block)
}

fun VerticalScroll(
        c: ComponentContext,
        block: VerticalScroll.Builder.() -> Unit
): Component.Builder<VerticalScroll.Builder> {
    return c.componentBuilder(VerticalScroll::create, block)
}
