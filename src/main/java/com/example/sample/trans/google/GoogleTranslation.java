package com.example.sample.trans.google;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleTranslation {

    private static Logger logger = LoggerFactory.getLogger(GoogleTranslation.class);

    public static void main(String[] args) {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate
        String text = "Hello, world!";

        // Translates some text into Russian
        Translation translation =
                translate.translate(text, Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("ru"));
        logger.info(">>>>> TRANSLATION: {}", translation.getTranslatedText());
    }

}
