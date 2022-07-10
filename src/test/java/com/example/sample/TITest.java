package com.example.sample;

import com.example.sample.util.HttpURLConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class TITest {

    @Test
    public void getLoginUrl() {

    }


    @Test
    public void login() throws Exception {
        String url = "https://login.ti.com/as/0zZFn/resume/as/authorization.ping";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "CONSENTMGR=ts:1654444583414%7Cconsent:true; user_pref_currency=\"USD\"; _gid=GA1.2.1796891141.1654444584; chipset=10814856; user_pref_language=\"zh-CN\"; tiSessionID=0181345a41a5001787358bf4f5530506f001606700978; _ga=GA1.2.957253377.1654440740; _gcl_au=1.1.1966357269.1654444734; ELOQUA=GUID=A824D3FD4C204AB1AD5ADD90550A9296; _fbp=fb.1.1654444740759.1942327103; user_pref_givenName=\"\"; bm_sz=D74ECC4EF02CEC0DD943ABC9246FF2CD~YAAQm5TYF0jn0DWBAQAA17b4OBCHHrhCnK2LfgJ/eefvSXvkYpr0oY0Xsy+DbT+w7lkidyf7xOqnQdbgau3OnWB7tCPtmwWqX1dKor5oDGtmpUvh1NwntKCBwS5gjgxllhuwbd9v0v/c/nuSdS3I5gGdtUxjcZBGkngdOKKe/YvQx0cFgXO4ohPgeK/dkryt7Yw6oimIkjbuhqwIupxJNivR5idJMtQ/GYSNfgySCbmTUP6AA50EWUp6iGD74acDL+j/jLcEqVr0Th/PvWNxRhaRO3+bxksm5wOSGsNNaQ==~3223602~3159348; ga_content_cookie=%2Fauth; bm_mi=FDD2C0821CC3F066A08B8AAD0350B1C7~YAAQ5G+bG5kcAAGBAQAA9yz+OBCllvNdc/UTJbdYaIuARQ8mdNP3FKW7z4UI8JnuaZSy65J0UxLC/Af+Wzdjz5aqMhlErteF5Dve1/oWT0PZyntcsBjlFQuB91fDM/CodDDf4qoAoTDumqDKprCvo3fe5ZlkznvSS+BayHBWfqZB7UY65Q03JSNwOCskZfbFf4rksUccnXePBudnsDEEI3rQXkP3bThpFKl77VFgm7WQA5b6pSIhr0yVFE9bkhs1iS4if2hGOA1L1K3Vn9NF2wCMN48v0JEI570Avt3+SnQaHQutLPS6pf2Zl9XpDSa28sO19fdvtNw15X/htBJsJ5TeTItwvJUADDhgZZvY79sH0RLejrmz9g4bT3k=~1; ak_bmsc=0F2D43162A38B30208BB3075743459C9~000000000000000000000000000000~YAAQ5G+bG7LFAAGBAQAAOXIBORCUXjARj5tF0Oad+fycpRpV7OTfjNq/wlFS551HWEOQqR7kGnCBr1I9e2wG5qgCBi5fMFpWI2uzQmo1Rin0KEQhhpV+Dfg1kudljAEpzwZkkaBcqsBoNIQzogihTqgnLx13eEYMhyugBXRsrYr4AxlsXxRZZfY2MB7Ninrn4qgB9sdE/subDIX3CxIwKx7LwGhGUyOK2QDtGKALuzMHRcakOxYDxxOxNrnSbxLBCfKGRtlG+ekTGcId4Qq3itN6hq00jSZWxt/gwEZiZqe2/3c9rZ337nw0sq+iE9l89AlfBSK17e3Iz7LyyrsNLfy4QVyILwAJC3MmX6byw6cvmh7kwgPaYwqR7vP98eW69C1LwyJAtUzkpWfITfaY/UEsIVcnzYmxp8MhVtT4YeSeFBswtRA2dpZQ0wrRJ+yy+dC3W0gzvP80BPuk4tD0U3gHYUKqB+I7bwBG; ga_page_cookie=as%2Fauthorization.oauth2-zh; pf-accept-language=zh-CN; PF=jfbPkJit28570CQGKfGhkh; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f103.0.0.0%20Safari%2f537.36; tipage=%2Fauth%2Fas%2Fauthorization.oauth2-zh; tipageshort=as%2Fauthorization.oauth2-zh; ticontent=%2Fauth; bm_sv=30EB82354213D83C747D4C60E921858A~YAAQBmgDF6Gipy6BAQAA5z8hORDAMXaYJ2nKyUXpT+l1SnMgYa9/HXViHWV17aqxeqKBXiF7A/1MSC3aPmQ/TNJerw0pLWVme2cPehbHHa98TcSPSjOhi88vcnX9QjjVki38PNda/AUJdpNqSHQmH0PmH13DxKH6w+HXbCgItPoet5VB8qG8bw+ofdqQpQRAEEHgdgcIQ4rF78sgSn/ZY6cLM5qaomIwdnNEYe0AaRHHICA/Rqg6v7XpH4dS~1; ABTasty=uid=asbx78j23e49bsj4&fst=1654444585902&pst=1654508144952&cst=1654518322604&ns=6&pvt=26&pvis=10&th=684039.848371.1.1.1.1.1654500711730.1654500711730.1_816192.1013836.1.1.1.1.1654500710978.1654500710978.1_832089.1034325.6.6.1.1.1654444734653.1654445460965.1; ABTastySession=mrasn=&sen=9&lp=https%253A%252F%252Flogin.ti.com%252Fas%252Fauthorization.oauth2%253Fresponse_type%253Dcode%2526scope%253Dopenid%2520email%2520profile%2526client_id%253DDCIT_ALL_WWW-PROD%2526state%253DyiRXM95UEZ4yOgXHUxdgjPn6Aik%2526redirect_uri%253Dhttps%253A%252F%252Fwww.ti.com.cn%252Foidc%252Fredirect_uri%252F%2526nonce%253Dt4QhofVS0g5d98EarF8-LJ13S6VybFyBPQq8ElHyG7o%2526response_mode%253Dform_post; da_sid=B3AE34D38E32AE96FDEBAA13B56E82056F|2|0|0; da_lid=1D8F10B29A73EA013E9FBB99F7795BBFF2|0|0|0; da_intState=0; ti_bm=; ti_rid=1f0e44b5; userType=Registered; _gat_ga_main_tracker=1; _abck=0EE1A4712C0F6522F5340BD7ACC85788~-1~YAAQBmgDF6ytpy6BAQAAarIiOQi/rZUmhrheIgIByB5ajqKwkP3NMLRs/Rz38dbE2h9olvNLc7yCXFe8l+gqijlvaRIPINi1vtRCgaYqBcV7gVFM6CDJbQ8bQowcUsMc8jIcp+M0K+eTh7uq8FKSDNR+iXRfc/naagL9RgZfF/UoDHqgNWw4/4pG0VL6rZKMxeHb4+OzBRP9MoBL8G+JB6d/QBNeDAxY9+fcXObevuekP/DFYKABxZtRpWxSUs/bY1CFCdMRYy38gvbvl7kUF2ZEa5fBrF4ug/V6Ryq/ISH06MotuufeAqVUNgM3+pOB3ADcQcwZUha0hHgarRiK4ulLgkUcf1OcAg/HbRKEeZ4RqwiYNAu782o30SUN5xG/tC2gxgruqa9lBPDcFynj9Jry81Ht7+IskknFz4BdT7oIvyV+xfW4mLuJXY44L6xh8AFfOjqx~-1~-1~-1; utag_main=v_id:01813494e9fe001e3df21f7432890506f012806700978$_sn:6$_ss:0$_st:1654522805844$free_trial:false$dc_visit:6$_pn:11%3Bexp-session$ses_id:1654518300590%3Bexp-session$dc_event:9%3Bexp-session$dc_region:ap-east-1%3Bexp-session; _gali=loginbutton");
        headerMap.put("Host", "login.ti.com");
        headerMap.put("Referer", "https://login.ti.com");
        headerMap.put("Origin", "https://login.ti.com");
        headerMap.put("Sec-Fetch-Dest", "document");
        headerMap.put("Sec-Fetch-Mode", "navigate");
        headerMap.put("Sec-Fetch-Site", "same-origin");
        headerMap.put("Sec-Fetch-User", "?1");
        headerMap.put("sec-ch-ua", "\".Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"103\", \"Chromium\";v=\"103\"");
        headerMap.put("sec-ch-ua-mobile", "?0");
        headerMap.put("sec-ch-ua-platform", "\"Windows\"");
        headerMap.put("pragma", "no-cache");
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("Cache-Control", "no-cache");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        String s = HttpURLConnectionUtil.httpPost(url, headerMap, "pf.username=test&pf.pass=test&pf.adapterId=IDPAdapterHTMLFormCIDStandard");
        log.debug(">>>>> RESPONSE: [{}]", s);
    }

    @Test
    public void number() throws ScriptException {
        String s = "VV = Vr + Vr * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "bM = dr + Gr * tr + Vr * tr * tr,\n" +
                "r7 = Mr + Mr * tr + Mr * tr * tr,\n" +
                "BE = Mr + dr * tr + Mr * tr * tr,\n" +
                "IM = Vr + tr + Vr * tr * tr,\n" +
                "DE = rr + hr * tr + Er * tr * tr + rr * tr * tr * tr,\n" +
                "m7 = rr + Er * tr + tr * tr,\n" +
                "j6 = dr + cr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "gE = rr + hr * tr + rr * tr * tr + Gr * tr * tr * tr,\n" +
                "IE = Er + Er * tr + cr * tr * tr + Mr * tr * tr * tr,\n" +
                "qr = dr + Gr * tr + rr * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "EM = Er + Mr * tr + Vr * tr * tr,\n" +
                "fM = hr + Gr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "wG = Vr + Vr * tr + tr * tr + Gr * tr * tr * tr,\n" +
                "CE = dr + hr * tr + dr * tr * tr,\n" +
                "gM = Cr + Er * tr + tr * tr + tr * tr * tr,\n" +
                "pr = Cr + tr + hr * tr * tr + rr * tr * tr * tr,\n" +
                "nM = Mr + Zr * tr + rr * tr * tr + Er * tr * tr * tr,\n" +
                "ZE = Gr + Zr * tr + hr * tr * tr + rr * tr * tr * tr,\n" +
                "W6 = Gr + tr + cr * tr * tr + cr * tr * tr * tr,\n" +
                "MM = Er + Er * tr + cr * tr * tr + tr * tr * tr,\n" +
                "lG = Vr + Er * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "b6 = cr + Gr * tr + cr * tr * tr + Gr * tr * tr * tr,\n" +
                "V7 = dr + Er * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "gV = Mr + Vr * tr + Vr * tr * tr + tr * tr * tr,\n" +
                "YG = dr + rr * tr + tr * tr + tr * tr * tr,\n" +
                "jr = rr + Gr * tr + Er * tr * tr + Gr * tr * tr * tr,\n" +
                "Lr = Er + Zr * tr + hr * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "FV = dr + Gr * tr + Vr * tr * tr + Gr * tr * tr * tr,\n" +
                "kE = dr + tr + hr * tr * tr + Er * tr * tr * tr + tr * tr * tr * tr,\n" +
                "T6 = Mr + tr + dr * tr * tr + Vr * tr * tr * tr,\n" +
                "xG = Er + tr + Vr * tr * tr,\n" +
                "xE = dr + hr * tr + dr * tr * tr + rr * tr * tr * tr,\n" +
                "AG = Zr + Er * tr + tr * tr + Er * tr * tr * tr,\n" +
                "MG = hr + dr * tr + hr * tr * tr + Er * tr * tr * tr,\n" +
                "DV = Mr + Er * tr,\n" +
                "SV = cr + Gr * tr,\n" +
                "UE = Cr + Vr * tr + Zr * tr * tr + cr * tr * tr * tr,\n" +
                "XE = hr + Er * tr + cr * tr * tr + Mr * tr * tr * tr,\n" +
                "J7 = Vr + hr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "QE = Gr + Zr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "D7 = hr + Mr * tr + tr * tr,\n" +
                "GV = Mr + tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "ME = Cr + rr * tr + Vr * tr * tr + tr * tr * tr,\n" +
                "T7 = cr + Mr * tr + Er * tr * tr + rr * tr * tr * tr,\n" +
                "A7 = Vr + Er * tr + Zr * tr * tr,\n" +
                "gG = rr + hr * tr + cr * tr * tr + rr * tr * tr * tr,\n" +
                "Or = dr + dr * tr + dr * tr * tr + cr * tr * tr * tr,\n" +
                "l6 = hr + hr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "Kr = hr + Gr * tr + cr * tr * tr + Mr * tr * tr * tr,\n" +
                "TE = Cr + tr + rr * tr * tr + tr * tr * tr + tr * tr * tr * tr,\n" +
                "FM = Cr + dr * tr + Er * tr * tr,\n" +
                "mM = cr + Er * tr + rr * tr * tr + Mr * tr * tr * tr,\n" +
                "WE = cr + Gr * tr + Mr * tr * tr + Vr * tr * tr * tr,\n" +
                "kM = dr + Gr * tr + rr * tr * tr,\n" +
                "m6 = dr + tr,\n" +
                "KgD = Zr + Gr * tr + rr * tr * tr + tr * tr * tr,\n" +
                "Yr = Mr + rr * tr,\n" +
                "EG = Cr + Mr * tr + Vr * tr * tr + cr * tr * tr * tr,\n" +
                "E6 = Vr + cr * tr + tr * tr + Zr * tr * tr * tr,\n" +
                "WG = rr + Gr * tr,\n" +
                "KE = Vr + dr * tr + Vr * tr * tr + Vr * tr * tr * tr,\n" +
                "w7 = Mr + tr + hr * tr * tr + tr * tr * tr,\n" +
                "J6 = Gr + Er * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "JM = Gr + Zr * tr + Mr * tr * tr,\n" +
                "O6 = dr + Mr * tr,\n" +
                "qM = dr + tr + Zr * tr * tr,\n" +
                "vG = Mr + rr * tr + tr * tr + Er * tr * tr * tr + tr * tr * tr * tr,\n" +
                "EE = Mr + rr * tr + Mr * tr * tr + rr * tr * tr * tr,\n" +
                "s6 = Mr + rr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "F6 = Cr + rr * tr + tr * tr + rr * tr * tr * tr,\n" +
                "jG = hr + Vr * tr + cr * tr * tr + rr * tr * tr * tr,\n" +
                "N6 = hr + rr * tr + Er * tr * tr + Vr * tr * tr * tr + Gr * tr * tr * tr * tr,\n" +
                "OG = Vr + Vr * tr + Er * tr * tr + tr * tr * tr,\n" +
                "QG = Gr + Gr * tr + cr * tr * tr + Vr * tr * tr * tr,\n" +
                "hV = hr + Vr * tr + hr * tr * tr + dr * tr * tr * tr,\n" +
                "tV = Cr + hr * tr + Zr * tr * tr + cr * tr * tr * tr,\n" +
                "wV = dr + dr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "UV = Mr + Gr * tr + Vr * tr * tr + Er * tr * tr * tr,\n" +
                "nV = Gr + Gr * tr + Mr * tr * tr + Vr * tr * tr * tr,\n" +
                "kV = cr + Mr * tr + cr * tr * tr,\n" +
                "OM = dr + hr * tr + Zr * tr * tr + tr * tr * tr + tr * tr * tr * tr,\n" +
                "sV = rr + Er * tr + rr * tr * tr + Er * tr * tr * tr,\n" +
                "MV = rr + Gr * tr + Zr * tr * tr + tr * tr * tr + tr * tr * tr * tr,\n" +
                "X7 = Cr + cr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "NV = dr + cr * tr + rr * tr * tr,\n" +
                "KM = Zr + Zr * tr + dr * tr * tr + Zr * tr * tr * tr,\n" +
                "Q6 = Vr + Er * tr + tr * tr + Gr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "KG = hr + hr * tr + Zr * tr * tr + dr * tr * tr * tr,\n" +
                "Y7 = Zr + dr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "nr = cr + Zr * tr + dr * tr * tr + Gr * tr * tr * tr,\n" +
                "CM = hr + dr * tr + Vr * tr * tr + tr * tr * tr,\n" +
                "q7 = Mr + Zr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "c7 = rr + Mr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "NM = hr + cr * tr + rr * tr * tr + tr * tr * tr,\n" +
                "lM = Er + Vr * tr + dr * tr * tr + rr * tr * tr * tr,\n" +
                "BV = Er + hr * tr + hr * tr * tr + Mr * tr * tr * tr,\n" +
                "t6 = Cr + Gr * tr + Vr * tr * tr + dr * tr * tr * tr,\n" +
                "wM = Cr + Zr * tr + Vr * tr * tr,\n" +
                "k6 = Vr + cr * tr + rr * tr * tr + Gr * tr * tr * tr + Gr * tr * tr * tr * tr,\n" +
                "V6 = Gr + rr * tr + dr * tr * tr + Zr * tr * tr * tr,\n" +
                "fV = Gr + dr * tr + Er * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "jV = cr + tr + Gr * tr * tr + cr * tr * tr * tr,\n" +
                "c6 = cr + Zr * tr + Zr * tr * tr + rr * tr * tr * tr,\n" +
                "H6 = Cr + tr + dr * tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "VE = Vr + tr + hr * tr * tr + tr * tr * tr,\n" +
                "AE = Vr + dr * tr + dr * tr * tr + Er * tr * tr * tr,\n" +
                "ZV = dr + dr * tr + hr * tr * tr + rr * tr * tr * tr,\n" +
                "R6 = Cr + Er * tr + rr * tr * tr + Vr * tr * tr * tr,\n" +
                "qE = Vr + Zr * tr + cr * tr * tr,\n" +
                "DM = dr + cr * tr + Er * tr * tr,\n" +
                "zE = Gr + dr * tr + Zr * tr * tr + Vr * tr * tr * tr,\n" +
                "hE = Cr + rr * tr,\n" +
                "d6 = rr + Gr * tr + Vr * tr * tr,\n" +
                "h6 = Zr + Er * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "rG = Zr + Vr * tr + dr * tr * tr + tr * tr * tr,\n" +
                "x6 = Gr + Er * tr + Gr * tr * tr + Gr * tr * tr * tr + Vr * tr * tr * tr * tr,\n" +
                "WV = hr + Er * tr,\n" +
                "K6 = Er + Er * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "vM = dr + cr * tr + Vr * tr * tr + Mr * tr * tr * tr,\n" +
                "I7 = Cr + dr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "VG = Gr + rr * tr + rr * tr * tr + Zr * tr * tr * tr,\n" +
                "qG = hr + cr * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "DG = rr + hr * tr + Vr * tr * tr + tr * tr * tr,\n" +
                "KV = Cr + Gr * tr,\n" +
                "v7 = Er + Er * tr + hr * tr * tr + tr * tr * tr,\n" +
                "L6 = Cr + rr * tr + tr * tr + Gr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "vr = dr + Gr * tr + Vr * tr * tr + Er * tr * tr * tr,\n" +
                "IV = hr + Vr * tr + Er * tr * tr + dr * tr * tr * tr,\n" +
                "SG = Vr + cr * tr + Er * tr * tr,\n" +
                "Ir = cr + Er * tr + Vr * tr * tr,\n" +
                "rV = Cr + Er * tr + cr * tr * tr + Zr * tr * tr * tr,\n" +
                "CG = cr + Mr * tr,\n" +
                "NG = Cr + Vr * tr + hr * tr * tr + Er * tr * tr * tr,\n" +
                "xr = Vr + Gr * tr + Mr * tr * tr,\n" +
                "pM = Er + Er * tr + hr * tr * tr + Vr * tr * tr * tr,\n" +
                "rE = Vr + dr * tr + rr * tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "Nr = cr + Mr * tr + Mr * tr * tr + rr * tr * tr * tr,\n" +
                "x7 = dr + Vr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "YM = dr + hr * tr + Vr * tr * tr + Er * tr * tr * tr + tr * tr * tr * tr,\n" +
                "F7 = hr + Vr * tr,\n" +
                "h7 = Er + hr * tr + cr * tr * tr + tr * tr * tr,\n" +
                "mG = Vr + Er * tr + cr * tr * tr + rr * tr * tr * tr,\n" +
                "k7 = Gr + hr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "HE = Mr + Mr * tr + dr * tr * tr,\n" +
                "vE = hr + tr + Mr * tr * tr + rr * tr * tr * tr,\n" +
                "cE = Zr + cr * tr + dr * tr * tr + Mr * tr * tr * tr,\n" +
                "mV = Cr + dr * tr + rr * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "JE = Gr + Zr * tr + cr * tr * tr + Zr * tr * tr * tr,\n" +
                "zM = Vr + Zr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "dG = Gr + rr * tr + tr * tr + cr * tr * tr * tr,\n" +
                "lV = Er + Gr * tr,\n" +
                "RG = rr + Vr * tr + Gr * tr * tr + Vr * tr * tr * tr,\n" +
                "OgD = Zr + Gr * tr + cr * tr * tr + rr * tr * tr * tr + Mr * tr * tr * tr * tr + cr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr + dr * tr * tr * tr * tr * tr * tr * tr + cr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "cV = Er + rr * tr + Vr * tr * tr + Mr * tr * tr * tr,\n" +
                "HV = Zr + dr * tr + dr * tr * tr + Er * tr * tr * tr,\n" +
                "BM = Mr + cr * tr + cr * tr * tr + rr * tr * tr * tr,\n" +
                "Z7 = hr + Gr * tr + Zr * tr * tr + tr * tr * tr,\n" +
                "UG = dr + Gr * tr + tr * tr + Vr * tr * tr * tr,\n" +
                "A6 = Mr + Gr * tr + Zr * tr * tr + rr * tr * tr * tr,\n" +
                "bE = rr + Er * tr + Zr * tr * tr + Mr * tr * tr * tr,\n" +
                "NE = hr + cr * tr + dr * tr * tr + Vr * tr * tr * tr,\n" +
                "IgD = rr + dr * tr + Gr * tr * tr + cr * tr * tr * tr + Er * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr * tr * tr + rr * tr * tr * tr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "LE = Er + cr * tr + tr * tr + tr * tr * tr,\n" +
                "PE = Cr + tr + Gr * tr * tr + Vr * tr * tr * tr,\n" +
                "v6 = Cr + Er * tr,\n" +
                "jM = Er + hr * tr + dr * tr * tr + Er * tr * tr * tr,\n" +
                "n6 = hr + Gr * tr + hr * tr * tr + Gr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "t7 = Vr + Vr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "sG = Er + Er * tr + Er * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "E7 = dr + Mr * tr + Er * tr * tr + rr * tr * tr * tr,\n" +
                "b7 = dr + Vr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "p6 = Zr + Er * tr + cr * tr * tr,\n" +
                "M6 = Gr + Vr * tr + tr * tr + tr * tr * tr,\n" +
                "xV = Mr + rr * tr + dr * tr * tr + Er * tr * tr * tr,\n" +
                "JG = Mr + Gr * tr + hr * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "mE = cr + Gr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "N7 = Er + Gr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "GG = rr + Mr * tr,\n" +
                "WM = Vr + hr * tr + Vr * tr * tr + Zr * tr * tr * tr,\n" +
                "Z6 = cr + Zr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "EV = hr + Mr * tr + Gr * tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "XG = Er + Gr * tr + Vr * tr * tr + Er * tr * tr * tr,\n" +
                "f7 = Gr + Vr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "UM = Vr + rr * tr + Zr * tr * tr + tr * tr * tr,\n" +
                "Qr = dr + Er * tr + cr * tr * tr + rr * tr * tr * tr,\n" +
                "I6 = cr + Er * tr + Er * tr * tr + rr * tr * tr * tr,\n" +
                "w6 = Er + cr * tr + Zr * tr * tr,\n" +
                "s7 = rr + rr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "R7 = Mr + tr,\n" +
                "TG = Er + rr * tr + Vr * tr * tr + tr * tr * tr,\n" +
                "TM = rr + rr * tr + tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "dV = Er + Zr * tr + dr * tr * tr + rr * tr * tr * tr,\n" +
                "C7 = rr + dr * tr + Er * tr * tr,\n" +
                "SE = Cr + Gr * tr + Gr * tr * tr + dr * tr * tr * tr,\n" +
                "q6 = Vr + Er * tr + Gr * tr * tr + Gr * tr * tr * tr + Vr * tr * tr * tr * tr,\n" +
                "fE = Mr + Mr * tr + tr * tr + tr * tr * tr,\n" +
                "RE = Er + Gr * tr + Vr * tr * tr,\n" +
                "sE = hr + tr + hr * tr * tr + Vr * tr * tr * tr,\n" +
                "X6 = cr + Mr * tr + hr * tr * tr + tr * tr * tr + tr * tr * tr * tr,\n" +
                "nE = Cr + hr * tr + cr * tr * tr + Vr * tr * tr * tr,\n" +
                "M7 = Gr + cr * tr + Er * tr * tr + tr * tr * tr,\n" +
                "rM = Vr + Er * tr,\n" +
                "z6 = dr + rr * tr,\n" +
                "pV = Gr + Mr * tr + Mr * tr * tr,\n" +
                "zV = Zr + dr * tr + tr * tr + Gr * tr * tr * tr,\n" +
                "Jr = Cr + dr * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "lr = Zr + Vr * tr + Gr * tr * tr + dr * tr * tr * tr,\n" +
                "nG = Vr + Er * tr + hr * tr * tr + Gr * tr * tr * tr,\n" +
                "JgD = cr + Mr * tr + Mr * tr * tr + Zr * tr * tr * tr + Zr * tr * tr * tr * tr + Gr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr + hr * tr * tr * tr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "B7 = hr + dr * tr + Vr * tr * tr,\n" +
                "TV = Mr + Zr * tr + hr * tr * tr + Vr * tr * tr * tr,\n" +
                "l7 = dr + Gr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "AM = cr + Zr * tr + Gr * tr * tr + rr * tr * tr * tr,\n" +
                "UgD = Cr + tr + dr * tr * tr + rr * tr * tr * tr + Vr * tr * tr * tr * tr + rr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr + dr * tr * tr * tr * tr * tr * tr * tr + Gr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "XV = hr + tr + Er * tr * tr,\n" +
                "G7 = cr + tr + Er * tr * tr + Vr * tr * tr * tr + Gr * tr * tr * tr * tr,\n" +
                "YE = Gr + hr * tr + tr * tr + rr * tr * tr * tr,\n" +
                "kG = Cr + cr * tr + dr * tr * tr + rr * tr * tr * tr,\n" +
                "ZM = Cr + Er * tr + tr * tr + Er * tr * tr * tr,\n" +
                "tG = hr + dr * tr + Mr * tr * tr,\n" +
                "bV = Zr + rr * tr + dr * tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "CV = Er + Vr * tr + tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "wE = Cr + rr * tr + cr * tr * tr + Zr * tr * tr * tr,\n" +
                "Sr = Mr + hr * tr + Er * tr * tr + dr * tr * tr * tr,\n" +
                "vV = rr + Gr * tr + hr * tr * tr + Mr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "U6 = Gr + Gr * tr,\n" +
                "P6 = rr + cr * tr + hr * tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "GE = dr + Gr * tr + rr * tr * tr + Mr * tr * tr * tr,\n" +
                "ZG = rr + hr * tr + Gr * tr * tr + Er * tr * tr * tr,\n" +
                "O7 = hr + Gr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "PM = hr + Mr * tr + rr * tr * tr + dr * tr * tr * tr,\n" +
                "z7 = Er + cr * tr + hr * tr * tr + tr * tr * tr,\n" +
                "jE = Zr + Zr * tr + Mr * tr * tr,\n" +
                "PG = hr + Er * tr + Er * tr * tr + Gr * tr * tr * tr,\n" +
                "LG = Cr + Zr * tr + Vr * tr * tr + Vr * tr * tr * tr,\n" +
                "S6 = Zr + Zr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "zr = Mr + Mr * tr + Er * tr * tr + Vr * tr * tr * tr,\n" +
                "pG = cr + Gr * tr + tr * tr + tr * tr * tr,\n" +
                "cM = cr + rr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "f6 = Zr + dr * tr + tr * tr + Mr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "sr = Mr + rr * tr + Mr * tr * tr + Er * tr * tr * tr,\n" +
                "JV = Gr + hr * tr + tr * tr + Gr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "lgD = cr + Vr * tr + Zr * tr * tr + Gr * tr * tr * tr + cr * tr * tr * tr * tr + hr * tr * tr * tr * tr * tr + Er * tr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr * tr + dr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "RV = dr + Er * tr + Mr * tr * tr,\n" +
                "qV = cr + dr * tr + tr * tr + hr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "dE = Cr + Er * tr + Gr * tr * tr,\n" +
                "W7 = dr + Gr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "Ur = Er + Er * tr + rr * tr * tr + Er * tr * tr * tr,\n" +
                "zG = Mr + Vr * tr + cr * tr * tr + tr * tr * tr,\n" +
                "QV = Zr + rr * tr + Zr * tr * tr + Zr * tr * tr * tr,\n" +
                "d7 = dr + Er * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "GM = Gr + Mr * tr + Er * tr * tr + rr * tr * tr * tr,\n" +
                "vgD = Mr + rr * tr + hr * tr * tr + Mr * tr * tr * tr + hr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr + Vr * tr * tr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "RM = Zr + Vr * tr + tr * tr + tr * tr * tr,\n" +
                "sM = Mr + Gr * tr + Vr * tr * tr,\n" +
                "LM = Er + hr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "g7 = Er + Zr * tr + Zr * tr * tr,\n" +
                "qgD = Vr + Mr * tr + tr * tr + dr * tr * tr * tr + Er * tr * tr * tr * tr + Gr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr + hr * tr * tr * tr * tr * tr * tr * tr + hr * tr * tr * tr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "HG = hr + hr * tr + Vr * tr * tr + Mr * tr * tr * tr,\n" +
                "bG = cr + hr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "xM = Er + Zr * tr + cr * tr * tr + Vr * tr * tr * tr,\n" +
                "pE = Cr + tr + hr * tr * tr + rr * tr * tr * tr + tr * tr * tr * tr,\n" +
                "H7 = Cr + Vr * tr + dr * tr * tr,\n" +
                "Y6 = Er + rr * tr + hr * tr * tr + Mr * tr * tr * tr,\n" +
                "Tr = dr + Mr * tr + Gr * tr * tr + tr * tr * tr,\n" +
                "VM = Cr + cr * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "r6 = Gr + dr * tr + Zr * tr * tr,\n" +
                "QM = Cr + Mr * tr + tr * tr + Mr * tr * tr * tr,\n" +
                "wr = Mr + Er * tr + Er * tr * tr,\n" +
                "G6 = dr + rr * tr + tr * tr + Er * tr * tr * tr,\n" +
                "tM = Mr + rr * tr + Gr * tr * tr + Er * tr * tr * tr,\n" +
                "OV = Mr + Mr * tr,\n" +
                "IG = rr + tr + rr * tr * tr + Mr * tr * tr * tr,\n" +
                "zgD = Vr + Zr * tr + Zr * tr * tr + Vr * tr * tr * tr + Vr * tr * tr * tr * tr + Gr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr + Vr * tr * tr * tr * tr * tr * tr * tr + hr * tr * tr * tr * tr * tr * tr * tr * tr + rr * tr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "lE = hr + cr * tr + tr * tr + Mr * tr * tr * tr,\n" +
                "B6 = rr + tr + Mr * tr * tr + Mr * tr * tr * tr,\n" +
                "dM = Gr + hr * tr + Mr * tr * tr,\n" +
                "HM = Er + dr * tr + Vr * tr * tr + rr * tr * tr * tr,\n" +
                "hG = Zr + Gr * tr + dr * tr * tr,\n" +
                "cG = Mr + hr * tr + tr * tr + Gr * tr * tr * tr,\n" +
                "hM = Cr + Zr * tr + Gr * tr * tr,\n" +
                "tE = dr + cr * tr + tr * tr,\n" +
                "xgD = hr + hr * tr + Vr * tr * tr + Mr * tr * tr * tr + cr * tr * tr * tr * tr + cr * tr * tr * tr * tr * tr + dr * tr * tr * tr * tr * tr * tr + rr * tr * tr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr * tr * tr + tr * tr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "fG = Vr + Vr * tr + Mr * tr * tr + tr * tr * tr,\n" +
                "YV = Cr + tr + Mr * tr * tr + Er * tr * tr * tr,\n" +
                "AV = cr + tr + Gr * tr * tr,\n" +
                "Pr = Vr + Gr * tr,\n" +
                "D6 = Vr + Mr * tr + tr * tr + Mr * tr * tr * tr,\n" +
                "kgD = Gr + Gr * tr + Gr * tr * tr + Gr * tr * tr * tr + hr * tr * tr * tr * tr + Mr * tr * tr * tr * tr * tr + Zr * tr * tr * tr * tr * tr * tr + Gr * tr * tr * tr * tr * tr * tr * tr,\n" +
                "OE = Vr + Gr * tr + rr * tr * tr + rr * tr * tr * tr,\n" +
                "PV = Zr + tr,\n" +
                "SM = Zr + tr + Mr * tr * tr + Mr * tr * tr * tr,\n" +
                "C6 = dr + dr * tr + Zr * tr * tr + Er * tr * tr * tr,\n" +
                "BG = rr + Mr * tr + Mr * tr * tr + Gr * tr * tr * tr,\n" +
                "LV = Mr + Gr * tr,\n" +
                "XM = Zr + dr * tr + Gr * tr * tr,\n" +
                "FE = Cr + hr * tr + Vr * tr * tr + Er * tr * tr * tr,\n" +
                "kr = Mr + dr * tr + Er * tr * tr + tr * tr * tr,\n" +
                "g6 = Zr + cr * tr + Zr * tr * tr + rr * tr * tr * tr";
        String[] array = s.split("\\n");
        // ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        // ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        Set<String> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            String[] split = array[i].split("=");
            String[] a = split[1].trim().replaceAll(",", "").split("\\+");
            for (String ss : a) {
                if (!ss.contains("*")) {
                    set.add(a[0].trim());
                    System.out.println(a[0]);
                } else {
                    String[] split1 = ss.split("\\*");
                    set.add(split1[0].trim());
                    System.out.println(split1[0].trim());
                    set.add(split1[1].trim());
                    System.out.println(split1[1].trim());

                }
            }
        }
        System.out.println(set);
    }


}
