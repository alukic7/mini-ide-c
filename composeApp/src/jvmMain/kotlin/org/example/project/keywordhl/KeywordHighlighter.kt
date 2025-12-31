package org.example.project.keywordhl

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import org.example.project.ui.runner.RuntimeConfiguration
import java.awt.SystemColor.text

object KeywordHighlighter {
    val kotlinKeywords: List<String> =
        listOf(
            "val", "var", "fun", "inline", "const", "throw",
            "class", "open", "interface", "abstract", "vararg",
            "object", "enum", "inline", "data", "try",
            "catch", "finally", "import", "package",
            "public", "private", "protected", "while",
            "for", "do", "return", "break", "when", "sealed",
            "if", "else", "in", "is", "out", "as", "this",
        )

    val swiftKeywords: List<String> =
        listOf(
            "let", "var", "func", "inout", "defer",
            "class", "struct", "enum", "protocol",
            "extension", "init", "deinit",
            "import", "typealias", "associatedtype",
            "public", "private", "fileprivate", "internal", "open",
            "if", "else", "switch", "case", "default",
            "for", "while", "repeat", "break", "continue", "return",
            "guard", "where", "throw", "throws", "try", "catch",
            "as", "is", "nil", "self", "super",
            "if", "else"
        )

    fun highlightKeyword(word: String, config: RuntimeConfiguration): AnnotatedString {
        val keywords = when (config) {
            RuntimeConfiguration.KOTLIN -> kotlinKeywords
            RuntimeConfiguration.SWIFT -> swiftKeywords
        }

        return buildAnnotatedString {
            append(word)

            keywords.forEach { keyword ->
                val pattern = "\\b$keyword\\b".toRegex()
                val matches = pattern.findAll(word)

                for (match in matches) {
                    addStyle(
                        style = SpanStyle(
                            color = Color(0xFFCF8E6D),
                            fontWeight = FontWeight.SemiBold
                        ),
                        start = match.range.first,
                        end = match.range.last + 1
                    )
                }
            }
        }
    }

    fun getVisualTransformation(config: RuntimeConfiguration) = VisualTransformation { text ->
        TransformedText(
            text = highlightKeyword(text.text, config),
            offsetMapping = OffsetMapping.Identity
        )
    }
}