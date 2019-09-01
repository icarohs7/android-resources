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

@Suppress("FunctionName")
class ComponentContainer(private val context: ComponentContext) {
    private val children = mutableListOf<Component.Builder<*>>()

    private fun <B : Component.Builder<B>> add(create: (c: ComponentContext) -> B, init: B.() -> Unit) {
        val builder = create(context)
        builder.init()
        children.add(builder)
    }

    fun <B : Component.ContainerBuilder<B>> addChildren(containerBuilder: Component.ContainerBuilder<B>) {
        children.forEach { containerBuilder.child(it) }
    }

    fun Card(block: Card.Builder.() -> Unit) = add(Card::create, block)
    fun CardClip(block: CardClip.Builder.() -> Unit) = add(CardClip::create, block)
    fun Column(block: Column.Builder.() -> Unit) = add(Column::create, block)
    fun EditText(block: EditText.Builder.() -> Unit) = add(EditText::create, block)
    fun Empty(block: EmptyComponent.Builder.() -> Unit) = add(EmptyComponent::create, block)
    fun HorizontalScroll(block: HorizontalScroll.Builder.() -> Unit) = add(HorizontalScroll::create, block)
    fun Image(block: Image.Builder.() -> Unit) = add(Image::create, block)
    fun Progress(block: Progress.Builder.() -> Unit) = add(Progress::create, block)
    fun Recycler(block: Recycler.Builder.() -> Unit) = add(Recycler::create, block)
    fun RecyclerCollectionComponent(
            block: RecyclerCollectionComponent.Builder.() -> Unit
    ) = add(RecyclerCollectionComponent::create, block)

    fun Row(block: Row.Builder.() -> Unit) = add(Row::create, block)
    fun SolidColor(block: SolidColor.Builder.() -> Unit) = add(SolidColor::create, block)
    fun Text(block: Text.Builder.() -> Unit) = add(Text::create, block)
    fun VerticalScroll(block: VerticalScroll.Builder.() -> Unit) = add(VerticalScroll::create, block)
}
