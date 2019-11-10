package com.example.sample.base;

public enum TtsLanguage {

    EN_IE("en-IE", "", "", "", "", "en-IE", ""),
    EN_US("en-US", "en-US", "", "en-US", "intp65_en", "en-US", "en-USA"),
    MS_MY("ms-MY", "", "", "", "", "ms-MY", ""),
    EN_IN("en-IN", "", "", "", "", "en-IN", "en-IND"),
    TA_IN("ta-IN", "", "", "", "", "ta-IN", ""),
    ZH_CN("zh-CN", "zh-CN", "zh", "", "intp65", "zh-CN", "zh-CHS"),
    EL_GR("el-GR", "", "", "", "", "el", "el"),
    TE_IN("te-IN", "", "", "", "", "te-IN", ""),
    NL_NL("nl-NL", "", "", "nl-NL", "", "nl-NL", "nl"),
    HI_IN("hi-IN", "", "", "", "", "hi-IN", "hi"),
    EN_AU("en-AU", "", "", "en-AU", "", "en-AU", "en-AUS"),
    HE_IL("he-IL", "", "", "", "", "he-IL", "he"),
    JA_JP("ja-JP", "", "", "ja-JP", "", "ja-JP", "ja"),
    DE_CH("de-CH", "", "", "", "", "de-CH", ""),
    HU_HU("hu-HU", "", "", "", "", "hu-HU", "hu"),
    FR_CA("fr-CA", "", "", "fr-CA", "", "fr-CA", "fr-CAN"),
    PL_PL("pl-PL", "", "", "pl-PL", "", "pl-PL", "pl"),
    PT_PT("pt-PT", "", "", "pt-PT", "", "pt-PT", "pt"),
    RO_RO("ro-RO", "", "", "", "", "ro-RO", "ro"),
    AR_EG("ar-EG", "", "", "", "", "ar-EG", ""),
    TR_TR("tr-TR", "", "", "tr-TR", "", "tr-TR", "tr"),
    FR_FR("fr-FR", "", "", "fr-FR", "", "fr-FR", "fr"),
    VI_VN("vi-VN", "", "", "", "", "vi-VN", ""),
    EN_GB("en-GB", "", "", "en-GB", "", "en-GB", "en-GBR"),
    FI_FI("fi-FI", "", "", "", "", "fi-FI", "fi"),
    NB_NO("nb-NO", "", "", "nb-NO", "", "nb-NO", "no"),
    EN_CA("en-CA", "", "", "", "", "en-CA", ""),
    HR_HR("hr-HR", "", "", "", "", "hr-HR", ""),
    AR_AE("ar-AE", "", "", "", "", "", "ar"),
    SL_SI("sl-SI", "", "", "", "", "sl-SI", ""),
    DE_AT("de-AT", "", "", "", "", "de-AT", ""),
    FR_CH("fr-CH", "", "", "", "", "fr-CH", ""),
    BG_BG("bg-BG", "", "", "", "", "bg-BG", ""),
    CS_CZ("cs-CZ", "", "", "", "", "cs-CZ", "cs"),
    AR_SA("ar-SA", "", "", "", "", "ar-SA", ""),
    CA_ES("ca-ES", "", "", "", "", "ca-ES", "ca"),
    DE_DE("de-DE", "", "", "de-DE", "", "de-DE", "de"),
    ZH_HK("zh-HK", "", "", "", "", "zh-HK", "yue"),
    ZH_TW("zh-TW", "", "", "", "", "zh-TW", "zh-TWN"),
    KO_KR("ko-KR", "", "", "ko-KR", "", "ko-KR", "ko"),
    PT_BR("pt-BR", "", "", "pt-BR", "", "pt-BR", "pt-BRA"),
    SK_SK("sk-SK", "", "", "sk-SK", "", "sk-SK", "sk"),
    ES_ES("es-ES", "", "", "es-ES", "", "es-ES", "es"),
    ES_MX("es-MX", "", "", "", "", "es-MX", ""),
    IT_IT("it-IT", "", "", "it-IT", "", "it-IT", "it"),
    RU_RU("ru-RU", "", "", "ru-RU", "", "ru-RU", "ru"),
    SV_SE("sv-SE", "", "", "sv-SE", "", "sv-SE", "sv"),
    ID_ID("id-ID", "", "", "", "", "id-ID", ""),
    DA_DK("da-DK", "", "", "da-DK", "", "da-DK", "da"),
    TH_TH("th-TH", "", "", "", "", "th-TH", "th");

    private String standardCode;
    private String aliyunCode;
    private String baiduCode;
    private String googleCode;
    private String iflyCode;
    private String microsoftCode;
    private String youdaoCode;

    TtsLanguage(String standardCode, String aliyunCode, String baiduCode, String googleCode, String iflyCode, String microsoftCode, String youdaoCode) {
        this.standardCode = standardCode;
        this.aliyunCode = aliyunCode;
        this.baiduCode = baiduCode;
        this.googleCode = googleCode;
        this.iflyCode = iflyCode;
        this.microsoftCode = microsoftCode;
        this.youdaoCode = youdaoCode;
    }
}
