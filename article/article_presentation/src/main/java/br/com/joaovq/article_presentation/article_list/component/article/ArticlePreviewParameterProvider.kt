package br.com.joaovq.article_presentation.article_list.component.article

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.joaovq.article_domain.model.Article
import java.time.OffsetDateTime

class ArticlePreviewParameterProvider : PreviewParameterProvider<Article> {
    override val values: Sequence<Article>
        get() = sequenceOf(
            Article(
                emptyList(),
                emptyList(),
                featured = false,
                1,
                "",
                emptyList(),
                "",
                OffsetDateTime.now().toString(),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "Solar Panels Now Capable of Generating Power from Moonlight",
                "",
                isBookmark = false,
                ""
            )
        )

}