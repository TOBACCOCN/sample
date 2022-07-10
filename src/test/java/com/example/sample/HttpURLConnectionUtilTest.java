package com.example.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.HttpURLConnectionUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpURLConnectionUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpURLConnectionUtilTest.class);

    @Test
    public void jpush() throws Exception {
        String url = "https://api.jpush.cn/v3/push";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        String keySecret = ":";
        String authorization = "Basic " + Base64.getEncoder().encodeToString(keySecret.getBytes(StandardCharsets.UTF_8));
        headerMap.put("Authorization", authorization);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platform", "android");
        jsonObject.put("audience", "all");
        JSONObject msgJsonObj = new JSONObject();
        msgJsonObj.put("msg_content", "jpush_content_example_XXXXXX");
        jsonObject.put("message", msgJsonObj);
        String param = jsonObject.toString();
        String s = HttpURLConnectionUtil.httpPost(url, headerMap, param);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }

    @Test
    public void check() throws Exception {
        // String url = "https://www.ti.com.cn/0hyISdl6v/5tzKJdj/1dBv4-w/GXuc/uzf1XSpc/STdCAQ/UQtDQx/dCCTc";
        // String url = "https://www.ti.com.cn/pEIPTOCGfE/H2PAjw/hW8C/up5LQcrS9wat/bC4gdQ8CAg/Xk5KfX/UlDGg";
        String url = "https://www.ti.com.cn/WPlqnqpyol66t8PTMtuReF4B/tO9EVDzm/GzM7GGwHGgM/YSlzFyo/obEk";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Content-Length", "6113");
        headerMap.put("Content-Type", "text/plain;charset=UTF-8");
        headerMap.put("Cookie", "ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f103.0.0.0%20Safari%2f537.36; ti_bm=; CONSENTMGR=ts:1654440739230%7Cconsent:true; tiSessionID=0181345a41a5001787358bf4f5530506f001606700978; _ga=GA1.3.957253377.1654440740; _gid=GA1.3.356447862.1654440740; user_pref_language=\"zh-CN\"; user_pref_currency=\"USD\"; _fbp=fb.2.1654440742872.1061395031; _gcl_au=1.1.1784990448.1654440743; __adroll_fpc=545347ec2f339635967d613e81cfb277-1654440743149; ELOQUA=GUID=CC6F22A3A83841AFB9B818ECBE0A96B6; _sg_b_n=1654440745767; user_pref_givenNameLocalLanguage=\"ping\"; login-check=null; acceleratorSecureGUID=5be9597dce3923baa5a83b8685e92dd980286e21; tipage=home%20page%2Fzh%20cn%20homepage-cn; tipageshort=zh%20cn%20homepage-cn; ticontent=home%20page; login-check=null; user_pref_permanentId=\"10814856\"; user_pref_givenName=\"?\"; user_pref_uid=\"1354953781@qq.com\"; last-domain=www.ti.com.cn; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=14.150.69.228; bm_sz=FF17FC15D4486D2E5516637A4298ECE8~YAAQlm2bG7DtrP2AAQAAd57qNhCTwv2hxfOTOXeS3XQawpW77X250XjRZwtHQSq38IAtWhxlmdQ6EucM7WjoTojfk6LBwWSBYHTQnSIoOFErrf+NJXignksJuHnJGUI3S2FNdWGAMVZvYOkX/zl8t1REpmQA39OA9IDMTd9CM3OvjbuK8oD00uznkBVIFOs/zoplpLqdVZ9BGx0Nrz5DV0M1jrb6flG69fXAjRAdEbC3OErIqlbej8V1Fm0LENI3vknrnbKgrPnb+CUPP0mv6P7j/6484/24Y3f6ezUtGr8/JdN15Jmkceol3+VUu37oDqRa8iFJRosJbuQI6HkuT/QpTsNrok3Unz3VcD9IUVBQjn+0IHc7u+iRfrXGCH4wN2+FwEbCb10EYkAF0Yr1~3616818~3294020; ga_page_cookie=zh%20cn%20homepage-cn; ga_content_cookie=home%20page; ak_bmsc=39A4EAB36EDD359103D88CEB0D0C5449~000000000000000000000000000000~YAAQlm2bG9/urP2AAQAAeaTqNhCV+aLjr2EjSmikAEbdAolitzLe2XSf9kh7x3mv9sUZwdLlf8SCSI65MYYabNMCxuRXGBSkXgtn6aVbnitz/yNWzJqgWPooW1IxGvjTIynZ17eVrDL9utJqmBSyx0OAK+qx/wSs7GjX2vdWlL8dxmvBx6pC0+i5+nh8dPPYIsosiMFKCip4BDVKwXNNIoXOkCqTHe4TzeJCgQ2znYOPN7AsqT8SnmvCTFhTbE0INbEEVLMU6bDGGemW5YKOp7+eVtfwBKwV1DDeC6S7DETubhkBUWY3SdaLyQKMAqm3nAs+gDhnLHGIPwUw2X15ZsGlmfflno6uTMh4kpw8PEfyU1Dl6nnhRTPWDo+HFzBR6RmdlB9CCDb6QzSKeql5tNyZrZjwvTOfJ8htIG2D1s9ZZP7Dqbi5bdqX25jjX6OuBiBA/l1xNp5+p91BOQ5JTDVw1BFsRVYJxMJ8yGuDk4MX1j0OKorQOifG; auth_session=rxHOec-Upggiqocg.whXU_rGu_osestBzInW_CE5aSLxbwn4WyyOafwiYfow9dAzzrEn1DI3SWZIH6__8foJzgDTw5D5ghV_tThkLMC47AvvR8Qgrf7EH-fsraMID5tC2zt0bQsoRwQHL-ntnRwWbfyBYizUe629kXMJLGZ0UFq8ZYRX3KZ5PxzWnKOZi1LPq5s98DNAlFvDPAYxk_3RxfK8fsb3jXXUJv2H_zSZB7u7InQk2pIqbUtfIoz9AZoNsQsbr_Hp891l56jsOrJRLnOrANCLS12HzbnHkMog532KJRo-nphcOdRVHRiNLngn-0Tkd2bSJHeNewRnboQZaTWhdo1tWtr95c-LMG7JZsaY8c29zMkWL8lwcklQ7ontvSAzi6UWOFMabo0ZKzDshymku1cPjFG15q3GUSykE9Cg8AUpb6P5rgkGqGVtLYvrPhhSZqpjHv7PbaTCvvFXEDp7gC8x34nL8yKhP-dwp3Oe_hl1g7mH4PaDMcLymRMYmY1UexNUDrn2WqSeiVuCtfzMY_02FP2D4llPPvYImiO2JZkVfkI16sD6zuSaTJbq62MicTpbdupdjohGNX5wxNBQPLishg194k2FYXK8Atnd5JkwBHczffPkxj9bgJFURbBdKhh2Tcxhk4Q4AifFG3v3B1CMIQhbord_F5UHhcM3VO0aTBZS7V3YDupBqL5URssCOlwCz60zd8mRE2VOngq8FmBCF09tX3DRigt956QhXhoJKR3PNYQtPfwSNapn9WVI4n4vj1UVrUG7PHcTuyUsQB1j8PUWsCjmaLWI0wexzIb1SPskNRxBWVqx02mJybmEWceIbeb7iCCoajTcO1bQynEu4rKsGAF_Oe2EP_sAUDTtdkdOWuRfwdYHPwqD_u4IRbIvtuz3aZaMTU0UiIvia3i6bbrVZp-GzMVr3VcaJuKbYKQe6gE7fkeS10kk9-6hE5YODeh4DQXGLI1_sjHRtQSnFhJIMWhTOFXwHveG64a-5L7enmYEOiBI-onuPqNAcZxj1S_khwuMYD8ht2N8eL0pHj6hQPssQ0zhbrhaRGMtZfnvaxZHyNabl9Wah3lh8Gmi5usS6SQK8mD7-Io9Tz50TsT9q3LgoGSghtKRkAFbPpMQQJ2Ynw0JnqKaX6MjdwZpqVwGTPOeLBZehqiWAmyvjAUXSiWYHqiKagZbgqLEaByNTOl8HtU5QqFZe5ztb_rCkMoVXxw6gd8FJu5OD-U9GckiKFMDY76Ju60K976L4UwVzhHXbuAS9NdUlBYvDRAH6d-ilYGfAWOegTuoM8oWuyslY6UnTF5692YaTqiGIy5itJykttXlTsS2uPh-k5QPGeESS8BfoHAKyX458BsnPjrAn-YP6IXVJifftuvxIoJDPFkFdse_ScMz4gk-AZbVX7c_vlnREKK3EkN3LkNhzx4llmMY6Jwbgcf5kPtKiuHBvlJNWveZl2jsEqgt60IXqSNnwUtBPyB6fC_qoREuHcFsKNbVD1G2WO2UK76i1GTN1OCBjWmf4GElYCtx9EwnI3KhDrD_OrEH4oi3JgyQGT3sk8g1pOA9tkGU6m7KaLSkFhPXjiBgxdAzTZPciA49EZQWm3s0jOI3_hylWsf4HMxMYL28rjcdcnu79RfCUuI-gXydmLxh_hEs-ZD06PHNjDyAXBKnxBZmw8CrsGg9K4BiAyEukahGKSjUpUHwfEVl7D6VQ_0z2_2R6P3qO7R55yTY_.Gxdq5AMOiB7oZsGlxp5S3w; ABTastySession=mrasn=&sen=-1&lp=https%253A%252F%252Fwww.ti.com.cn%252F%253F_ticdt%253DMTY1NDQ0NjM1MXwwMTgxMzQ1YTQxYTUwMDE3ODczNThiZjRmNTUzMDUwNmYwMDE2MDY3MDA5Nzh8R0ExLjIuOTU3MjUzMzc3LjE2NTQ0NDA3NDA; ABTasty=uid=q7maddze47w6zh08&fst=1654440740850&pst=1654444604468&cst=1654483758156&ns=3&pvt=7&pvis=2&th=686831.851794.6.2.2.1.1654444604966.1654485161783.1; _sg_b_v=3%3B41167%3B1654485162; _sg_b_p=%2F; da_sid=5C752F108E33AE92D4D3AA13B57B76BDC9|4|0|3; da_lid=6F461C239A73EA094182BB99F7793CB67A|0|0|0; da_intState=; __ar_v4=QFXRHQEHOJDMLHSLFIWCLO%3A20220605%3A6%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20220605%3A6%7CG3YHLXUICZC3XKDXYVVLO4%3A20220605%3A6; bm_sv=B9720BA5CB19EDE843CCACE2FF576FEB~YAAQ5G+bG9NVqACBAQAAihsANxBgpTe9ZoSwzM7fK6GcKAmpAxaAz+UtxY5cy0Ch0CpCf0uXPLTxDB7/1serLPhMCuH+Mf4F5BcVAmM+KlydWR0wmmygDfVlGV0J6oogLXx7s407KRm6xZXp1qdIGtrrBQ8WG84JgCIDN54L3ZJvhw1rijJ48EHjYm0191g/ZSh23GBoN4wMYHBpo9s1jCeLemXdPiwURmB964smQr4j9TyXSRRC2ODU/D60Tilb~1; ti_rid=1dd02e6e; _abck=8761041AD60E612945E0C8D82558277F~-1~YAAQ5G+bGypkqACBAQAA5n4ANwiVMlHZ5eN+uIJ+2s2rNxRuTV4EeVObTd7rCnoD+ZGFQfqU0N/JNuAwwHFPH8ljS81FvQp0N7z9VIqLWSwwzg3h4YTZKqtBavE6l7FiGdvMxHh41OvI4rXd6URKze+W5cwncf8sUZOyxRuArbKNgQ1N58+RkiW4xFzbq/InK71KVwe1me+LXlQpiEGulSY0ay7VN6MpDPgR3cq8+y3rwpVpEM7IV35QJ0V9HWI1dVnDrt+xv/FhpnFEVCqOlyNfpaE2RTnXF4gcX1cv6jnOHhJDRvyYkRaI8cB92vUPCSvWZ+EOJtkzBnkdn7GjBl+RR9vNcv3CbTmUPSw68kc6pDBqIAv9BfU5R1RCRt6cHNtNfTZm6kDfDuv7x7sJRNOXZDGc5cfw6Oxee9bKqBTR44rrckERhEQ7pJZIv+TMyY563deGHHvcv6QgAkb4PgyMPR4FMP6UbJeblma2jlJup3kWCrtm4ZpepadE~-1~-1~-1; utag_main=free_trial:false$channel:organic%3Bexp-1657036580952$ctimestamp:Sun%20Jun%2005%202022%2023%3A56%3A20%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)%3Bexp-1657036580952$_st:1654487003520$v_id:0181345a41a5001787358bf4f5530506f001606700978$_sn:3$_ss:0$dc_visit:3$_pn:3%3Bexp-session$ses_id:1654483756949%3Bexp-session$dc_event:2%3Bexp-session$dc_region:ap-east-1%3Bexp-session");
        headerMap.put("Host", "www.ti.com.cn");
        headerMap.put("Origin", "https://www.ti.com.cn");
        headerMap.put("Referer", "https://www.ti.com.cn/");
        headerMap.put("sec-ch-ua", "\".Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"103\", \"Chromium\";v=\"103\"");
        headerMap.put("sec-ch-ua-mobile", " ?0");
        headerMap.put("sec-ch-ua-platform", "\"Windows\"");
        // headerMap.put("Sec-Fetch-Dest", "empty");
        headerMap.put("Sec-Fetch-Dest", "empty");
        headerMap.put("Sec-Fetch-Mode", "cors");
        headerMap.put("Sec-Fetch-Site", "same-origin");
        headerMap.put("TE", "trailers");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");
        headerMap.put("X-Sec-Clge-Req-Type", "ajax");
        String param = "{\"sensor_data\":\"2;3616818;3294020;16,50,0,2,7,54;b]r7teM92!p:;pevE9{EZ=_T<1hVtHy[`4`xoDHx-gVGH- ElZU#=)^@55p7/!Jg9U0XH-<!HHXI]=+]1.0^v_m|W cXLMRw#*`( XWNU6L:V`<f>LB{A&~8dbWs>}Y069rB?*7#0HhkG:?fK^f9dZ>H7F<H3v?~?Dr[VPesA<pz,7Gd;WiEluVj`ospk^<|-<%Sf) 3tj@TfqqW/Td?Rvo+!0t.]AuZSiD[`a6ju%m/?9L4#BW?s{j.)NLxplN^Px~E.2=DqDx3/cnkMz202mr+3Vb$q1pf(KAHGIxZ?[FTj!ZpbzJaTgp!8ug+GA/)K%tkx6myDO&@-9W&;}@--?Jf}Hb4<U>:k8;6PePyY0}D%6TP=s.bQk~N`6J#8GDEsZXZn9!F9hX2l1aln0pqi,A|2|7e#J;?(yb`KUCwq?;wd!IQl`u~EGcJbFhEZ)!,eTUlHk^-.xkH7`:E[9t~%]{R~`2p6FOFeKVqIm=>I2,t^=y(p g -^!b9yRQ]29S6-fY4qcHPB$qSa:LfeMe%S{^;:p`wQ^|mc#=~X93;.7D5vJ[+*jxZrG60[MP$jDH8LUlhL@1D9O>S9s:cZOt8!J?eY$^5f8PbYx|/$,}<47%biatRg66S^;g!D~$ZFg?ScAeFS1/F<h(Q~|9jb[SHj:4|o/fx`*R`oG{nn)~5FikzfaIr$*e8+zoh;mrr?pL_h< ({`<fz<7@1~8uz0o=d~/|691.-S>S]9$5j$5U3=Dm`8U;ILm6@6?>2.F%e5n0B%s_W$*5w?[/#fD4J_I2<~><biqKkUJf&hMjs<X&CuwD2*hP#E0s |$6Xfz&:oh%Oc*BfwEmD&C:r1)(=gS<TWu*Pg{$=(ql_ZV!*`yc(h8h*g;.g*(q4/Wj:Ot 70/R#wu>Z1Z5{S,XmH>7o8?$O[1Kz6_k |#J3&-HqS|>=nHop9&pGOB>gwLmC_8DkolJ%07U+lB:hd.,K{&#]|2m~3UbGY@f?SZ7xy._8tbR(^_T(U1nM8@Nv<r@ D.By!:n3j!a*HUT?;*jHz7J)t5Fj:hPU_?|=H`O;Od@|t^hM?,9kC/tp^dog;z:Qo-|iIOSwKjcF]DYFD(0tbz25x:[0b$-$#Q~uv/*(RrM(;36+@%tX(,PHCg#c8%SLuKyPw;98U(~_kZvi||}_^)VM[>9x`WHF1,oLhAGFpD&y7~HJk]r`V)TNXo0n)+aqYd?)EmZGnCE/BF_f<;M0+m?TY&bZA>)8*0%sFss*gJI;N]~&0Qpbug1@`!PB==6VCP@4j0:CS<95Yf(8m&?7@ cq%iN?w$hb[fci>(4K2z&zHQ,q$/cZAL1t@/1g+nDXf$oz<3;hC4v~j}^*~YOzCTcZrFg h05TZwp(h_}74m5?s-s/p)Wp,xj.MD18Gep4Gee]H/)TD=|j2bMpTrUbxukCf^PZ_3:{,NTq[T!`_ON$a4yG*]+Cvj|M|aY|s:4Md)@bVP}TXV|@}N9vb8q1ZZn:rfkN7uEd*ut6A)oNS/tL.aZ1#_F42A@|ajd)HuF#G.;YaeB$>Hn?5daC0s}%X$udHVd.4wA%[A[&3.|j&&_(([bRH*sI,6M1t&=`2lI2TE^5FZA4]a>jaRVD:&x`;WFMF]xH!z0|n6lET!e[k/-KH6A15(uT=]/siGBvDEwoJ=`:^#rd*@Cu#ootzvy^C[$!{M[JxbFE(}O@X$to/>8bI:CO[W2AyCR3~KuZ0+m}[Y:{Fueg9gWKKMyu2,+3{JIr(Pye=JM;})fO)J`X;g`Rjr~4FLiSS%Wq}R|]Sv@P_>+vL5BB}SPRku* B54xwa3I_%l]TOQXUdTEyVo%oA?7^10:n~JC?~ay-qG>5UEbciYa5 TG#aw{LQ#Hs}KMQL*Jd;#Is7GIXjcaK/W(R6UQ2Dh#[#KKl[KW|:.(+~p.O_ch?=Uyoku-VtIn?n<HcRTu9Yby==Hm$IGZEHb##w?Ns6|E0R-0WF<{>0{.GonIZ}0B1*`$#yBU.`?W3G^u/A:jo3%BO,;r!Mhzku?~xpA^?lP;{J/FvnyN(()HQ1F|Lr2B>K1bgc:x9rc9*lMzENV2dwf>Q*O0O3s^x>R4Gj(KQkiFB!i,l5y`^E[CD~^!]8c$/5rUmyg(ziz;9>jEA5A@r.4f f?$Q:`19LC~4I8ia=E-:voB}FiJ3CT8eAww?.M)B^E;MuT72w~e!h]?3DhUFd{T0vUBvs= ?TDH+#q`P9RKaQD|w2D9vy0e@@t XIsZzIAcd!y[g[ow)w,s_!]RRw;raT>8tqS;g.OhD]W?Jo40L3Jih=dv}Q~<}GFIAD+UaGP}H#dS$#)5kI_I<eIMA0`wVYQ]WS/,~G]mE_%55XNHpp8$%::Kc?f^>WKfUfNJpa*~N873L`5Fnu99<.i|(qB=+<=XU`O`,S_g1beqAFx7]~<9K/&4=+rsjwrEXXEe-t-YK*94dAPugV)9F< @hb(7JBRotH6D+{6`S:OQ0F)!{Hp$A$|AdH9CV1uKfjVlDd/OJ:[0IfLpSuI` uf:cRFlg+4u?GO LNjIQ@c|M%}2hMyC`WG!VYIe[-Q#Dfx5~(Vtoa2CD>?qc77O>[nEnh*Ke@O)My*SGv`?DRzWOxtA<uV|DFR(pm{?Mf*`,g9bdta9:%o>2ueA2r[!UwvU#!zx9h<+X6Fut)i[mzT$qIVW4e^+_y,qcq,TlbB Q/9gAkiDZ0rPB1/uucW(Zy4$r*7n9#WD*a3d%L&oDE1ahOLJIKBj`}:)%:*V2>!AZ:nz?~RicZEItrvt~uyRlk.#}jZmuY9?)AJR~o1Xa}=#^xk0B<E3j?hmi-V@|qLO8?kUpFHCk83!.$M>O^dQstINT{;-ixT^KR1o9~x%Su~o$ GFbhxo`@v,vW/|edIURU:/k,BBb8*Oji)Dbh3$,^88O(S]IP>R?RE:nGa_.jO(ND{0St)*+g&@<S&#t,*80KC9uPntlETR|)ZpH0mt0oGh0nJOCR^pv2@+i.Kf}Ueq:RVOTC.cy[Pjk#be_*%L|hkx]p2^z5M,& Nuu:k#g%D5+$iuOkIqtf>xlW#zvc~Z7Ya83;a/4.~X9rTi MW{-Vbrgm1dY`<%^>fMDT1]^`VBz_J)wH`1P|z142Zm2=^ X.sB3eG^ (,cv#[]c0Y0Ut4OQR)Zeo=OHqrM3iQqESW+ggp7P|O-Qv0a_`L~)>f5&TL/$ZC{K*13,03b _3&4jFT`mc8E.7lK0CVT~ >:oS&A:LD+k7)nQwJ[rL@YmH za]KP*#e[Oqx8Vk<lX$!X6g2FbZ<ayW05!soyYc8*FVW9T3.Z8#88]$*54Qk!S;yqr$4B-(~;YurzV!S)l:3^-*7C_^HA7I4LNE@g>U2)QoroE{@ 7RfCCrf%UQSz&zR649Ed[`SF!TXT$9vQ0ZbEC5!JnpyK!qhy)B=[n a27qv XzjTUDPJG(xD{=9pSLHS`[.WgofkCu3dpFB3)%D5A#|U7>R4VX1)>`mBBG<8S08K8`dS[.nq,mmH!HR9w&~T -H/|TQ`D/APEq(p#W:EcId3$7OC*8;xrVvIl0/A*|<Qnz)+BOt{%+5^@RO5!8Ly0k/[~Uj|xW|AxbR6WXz*HG[P:j*&Yle~6x:uB!/LEBa1ti{~RY.7sv!s_@,Y_241xKtoV0Kjie4_e`qyGV6<krAP,aL-/VqR{j9&[<V%x!i+51DO4Ch|;)D?JzTjf*B)j[:!]cX-3>}QQQw@y6lFq#DIQ2$t7P/nD3puG<d8#A(Z?&N$FuG?gS{p.?#5J%E?e@BN<X$AQ(D8#WytJ?/:..zd^o,mY<IP]R*F!*$=0sWxxcwC?ys1^?O~:]il#6eqMEhlF(XWC;LT7<Y?Eq;IArh)J&CA+8sxU*,Yq*1uoZ]u~MJTp}?:,%/z-`IwozY]#|A|Z/|e%^!`d1nia|@X)E ;#dG6J=!Pgyg^=q2vI@^!*>#y(0-@rMdJ/>&#B!uGCDYFxOUoDX%BH#6XB^{8(_iyNWA5HH$U`i{]_TvNB%+v(K^xYKoPdfq`PR>!kd&}A dMXnM<9#PM}Bp^^*{/Y}&@wAG!oF<Cb~>QZP+0k=1:wc3`tMw.>[UKY4T;.XmeRG%&8L7wx{[*-]!cw)m2Q|w,WLn)O`:-SVfxbb18<Q7-^GxX43Y~]@z,aY~5_:,>]<mW{,KyHn7{/1ed}faYO~F.n9>@2JH/c(d&,?ky=[Ks>x`o@,]I#(y[hTFZ.|9>36x6kLI).1JGH1hoUs919DFsocqiUOuODZS>sGv~_pZ#-5@dLh?h?U%t#eHhP0l<ZLvFtei<3wY8INv%nN}>7:cu&]DNd4@8`5qhQhr>a)L>dR$J(*8oP@JK9x,y7lo;@HZ:zhJU7X@+>Pzw2cT^m;@QEVM<v1=`O~>u/7DLtWN7%jpETdR=~R4CaTLw3qQ]CRyRHD.m2fzn>[Tjj5mA?mN?($~M`aD9o>KXWO=|?r{pDXGp=[Wh`k~sBq6ec-o{$,6IB$>[`b34_*k(*2jORk!HF!WRTN9KyqAi:x;0lrKp~I4#{l|m<PEqH&R9sk^ru=Dcu.(uZ2 6U[V5I3D?F-,]7TW;gmh}6Yy3KebY+fhW4YfTT(`g$kb;z8L,j%pEFs2`k.C<~p/9$aY0K5&-7&*rRh%lUyh?Q|Cn/Y^m[Df#lNZQfl<7LQ]<,;hWBVj YJL `k 4ssGXGjwgbf$~@.pYGLsR$5Eu@xL~Ct J.3U(Bn~;CT3@ZZwgT)TbHh|Rw>)#cr4k?O4A5*NWzBq O^ukY2nhj8H)Vex]sHQYNP8|[S.y^/Ac4Iui`:jyr+Cu)tF9,SnDBJXVmf3mstXet&GGP+jy$VsfI4xnL([/9I*`f~<]2b2f=V$+=yXj{Xw|W]0*hb|j8+^W`e=VHmG1}+M<P GbpL~}]^=@5.P/LiRAS[0]EvT:A@s-53 <_8enUbGD:OoX$##f%?pM)c^/.RBMZVN2%fXN4E7$EGS8#X?cXJ^2yT~/Z)=]wqh</Jc0T:3=9>+m>:_aOE)c*8E uUA`Y)N~utb9Z>J+):!bKrhL5^J47x~^`C.Xz_h/;+l%(@Lo^{bV72ynU&gJE;T;3yx5v+1k86&DEgsD?NBC-wbxexlU`abXmTOzSu<^.R1W ~3^#(syYMse<vxrnk0y6vY1bG_9l_cR_~=n!EKQ3wBO2bo]pt46OA9~Xw9(gtWx!9L|)YXt]Gp*o%MLtp4<K9g71>kL8Uh5P-:,Wge$td6W:{49]_z9!#IL.`+zA$;a4],f( qG* EkeYiA*Kpyu=fNFd?92>fY^@j[xTl(*-W*}m(2ayDb)2ILgr@V;a!`(jI;^Ecv!*h7.*8W(=wA_x@(-iDMIs7%e3ky1~_dlpX)/&E]=Y<VDMtIoG*AHi=2WP?#cFoJOfFo%2WrHuVp_m@PS-s,1f0yfX,uCk=e*>ucm!KfJrV0+.Tw*cK_o`)tZ^AR&2^5OfSDQUr=isY-xIt,?,~6T7ocGRJj-1}JX#zlz7r7+cO-T GCPA=b~476+J.813lF+{jWz>0-Fp6ih^a#.1JqJGrl}xMiRWhOy:=:e%iD;+vXTt>k8S])UI339DF^Spq=nJAFx5t^~<B11rV(L]v2VXMOQyi3-<:6f<;< `5l4Xp!`:6bg>z)T,xgf+k`Zu=>d&|97ri|}z#IRx_An(5nxMJ:Alurw<04oOatW,}r$~9+;,&b2XcI)60Sc-|n|CJ_--VS/nrZFk.4I`=kdx;zZswB^*Mw<z`kd>d~jGGFWa|#N2NIz5m:0DRs1m)s7U[nezxA:f%yBM`s38G0~9u)QayYsBtYmOLt`b(JG.:Hk~IYwx@/~Iopfz4lAxJ@S ?[X`4]O;_f3:w(Pex{58cun3Y,PKl`,s(TIF$PUDh im;j~=TYO#X^d;F=u]7wSv>N5-fMhBj;SmPnOc),-n<ST&/y?f@;}x~NHFp392fEQy]*3LL^_DU3AtG2ph%<{FkKX9}ZBX|>%r51@8-oNeH:JWU!*a}r_RK^m%E6N[cK=4 ]fE2!3}d-C+[T<R@GxzSGXooUrTT#iLMxk%k!>*}>gN.#s~v*lm?:_(zn)=$rA~8IK|j#J1)QwdDEGTN4IT?\"}";
        String s = HttpURLConnectionUtil.httpPost(url, headerMap, param);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }

    @Test
    public void getLoginUrl() throws Exception {
        String url = "https://www.ti.com.cn/secure-link-forward/?gotoUrl=https://www.ti.com.cn/";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; " +
                "ti_rid=1a9f59a8; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64%3b%20rv%3a101.0)%20Gecko%2f20100101%20Firefox%2f101.0; " +
                "ti_bm=; _abck=8761041AD60E612945E0C8D82558277F~-1~YAAQrW2bG2XMZyiBAQAAMJ3+NwirEcuK6DxY6tcn5AOMda/iLli5uK9m45wsY4ThVz8rPm+LZ5vy45JfTmF4XQHJLVpODLCmCZvMlrgqBkuPTVkPNR9UMWibuLmAJlrxEHautjhkNQNITmJhZzvHAfXMcFB4ZR7dKjrYwbgceWL6HsmBZ8XQJM5L8uBT29xg4gZXcnKoDVsaEJ2RTwlk3RuNcI+fC+PzZd+w6kVNGDQPcIisEEeap3Mj6Rw4FeaCUJImtts2E1IjLReMx9IK0ZYgjQhTp8uWFNkq89Z+eQzrtRzDo9Ysp+7n0Ls9qA1ErOLfUVMCBeXpeoCI//tZyB/iwlYEK4C133Q1Kc9h0EAC7bZ+3bksJ85hSMdjfMAlqAq5SDHnI3dCaF2LKld3SqOr+KBImtAdDq9ZL2q5/CPToygTfknblm1Bq9GqUU7tY5hrnV0VoWNo79vOwiQy1DeVn9HZ4jEZ0EwAXxuZXRlPQvSYt2Jl3YYhP7Rhii0=~-1~-1~-1; " +
                "ga_content_cookie=home%20page; ABTastySession=mrasn=&sen=-1&lp=https%253A%252F%252Fwww.ti.com.cn%252F");
        headerMap.put("Host", "www.ti.com.cn");
        headerMap.put("Referer", "https://www.ti.com.cn/");
        headerMap.put("Sec-Fetch-Dest", "document");
        headerMap.put("Sec-Fetch-Mode", "navigate");
        headerMap.put("Sec-Fetch-Site", "same-origin");
        headerMap.put("Sec-Fetch-User", "?1");
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");
        String s = HttpURLConnectionUtil.httpGet(url, headerMap, null);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }

    @Test
    public void login() throws Exception {
        String url = "https://www.ti.com.cn/secure-link-forward/?gotoUrl=https://www.ti.com.cn/";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; " +
                "ti_rid=1a9f59a8; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64%3b%20rv%3a101.0)%20Gecko%2f20100101%20Firefox%2f101.0; " +
                "ti_bm=; _abck=8761041AD60E612945E0C8D82558277F~-1~YAAQrW2bG2XMZyiBAQAAMJ3+NwirEcuK6DxY6tcn5AOMda/iLli5uK9m45wsY4ThVz8rPm+LZ5vy45JfTmF4XQHJLVpODLCmCZvMlrgqBkuPTVkPNR9UMWibuLmAJlrxEHautjhkNQNITmJhZzvHAfXMcFB4ZR7dKjrYwbgceWL6HsmBZ8XQJM5L8uBT29xg4gZXcnKoDVsaEJ2RTwlk3RuNcI+fC+PzZd+w6kVNGDQPcIisEEeap3Mj6Rw4FeaCUJImtts2E1IjLReMx9IK0ZYgjQhTp8uWFNkq89Z+eQzrtRzDo9Ysp+7n0Ls9qA1ErOLfUVMCBeXpeoCI//tZyB/iwlYEK4C133Q1Kc9h0EAC7bZ+3bksJ85hSMdjfMAlqAq5SDHnI3dCaF2LKld3SqOr+KBImtAdDq9ZL2q5/CPToygTfknblm1Bq9GqUU7tY5hrnV0VoWNo79vOwiQy1DeVn9HZ4jEZ0EwAXxuZXRlPQvSYt2Jl3YYhP7Rhii0=~-1~-1~-1; " +
                "ga_content_cookie=home%20page; ABTastySession=mrasn=&sen=-1&lp=https%253A%252F%252Fwww.ti.com.cn%252F");
        headerMap.put("Host", "www.ti.com.cn");
        headerMap.put("Referer", "https://www.ti.com.cn/");
        headerMap.put("Sec-Fetch-Dest", "document");
        headerMap.put("Sec-Fetch-Mode", "navigate");
        headerMap.put("Sec-Fetch-Site", "same-origin");
        headerMap.put("Sec-Fetch-User", "?1");
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");
        String s = HttpURLConnectionUtil.httpGet(url, headerMap, null);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }

    @Test
    public void query() throws Exception {
        String chipModel = "LM324";
        String url = "https://www.ti.com.cn/productmodel/gpn/"+ chipModel + "/tistoresegmented";
        Map<String, String> headerMap = new HashMap<>();
        String auth_session = "qBYbibgrZ8J6ZzLf.AvPBOS-QqIXSmEv-oySXkgzMmrqSBkP9dGEof65MDixOWyGUjSruY0WAShzB5fg1kWiPjmwOImZL-71y2oGoBWkqwK7bt31MK6IEJEMkwzVdWz39AbYgZE0vgtWzX34tLxnSz3tiMTIJDL0ebLaQoWdCv4Il0ApfcoH7sgUqyclwJ_laE5mGGjEof0zjRs2y0CtRKawHai0atUNIE5PpPTW0rnr-4nrt0kuFUxNmJdyBH7vyvz_LJOVdvNmot2jUMQ0Gp9RW188YFazfbx4fO9V_W_LOFygUAD9RPrU3eGLks0gv_niP6ynC4tjl5wkdscdk7z5EqpJKIiCyskwQQ9qyXKKH28I8WTRM5fcWBm6KhmPQFcGqbxq4gG8Bl-lWV7cPKI_gekIfHPUwCxyRPGyQbK9nQ9G9tDptYeutkPqWTQzafTfghIRNNlnn_vdWeG1QusomQy8bjnSAg88W3JcVzodi5HdwYyFvROtOkl0aghNaQy0OkjSlnV3YsjlkGCxfEveiB-C9RSI6CugzBaOMfaHJYltCeFYB1LuTK1I86xDoCA1OVV0Nbi-3A2NRX4_jHYOIVuRF3hE7yw91AAG4p6NQzBn1EMKzmiuZ96HtSwG4CuSmWOKHMrQh7qSfbntEPIxGIFrjZHvMsEZ_af3_njl1yuqktKkToZf5dCwIygbMGxxxDkUFM3FG8umo5xPICiB9BbfTRhNmCVW9PLW66CN4Rcig3RLcO8qh7VFN7nM-vxHbAh0nYmm7Cmzq-78qafc4EEYOl15JuOyDgBssz0OTxZQWquaEcElYoqtOfKIJt9CTAU5vff9O5-n-SrN1YHskxn-QHVOj3yFssRZR9pOo_Xaep8erChknzELpGiVvp-b5jGe5J_p5_YWztApbSsFQPb7Lc0QtWxs2tZHzRsIRYbny020G4QZJ1lrO2T2jy-XgU2D-Q9T3GcNDQ7I_izYbdABLmz2MDkILwO4KQgS3iaigzig6rDLc1sqn54HjfhVTt3yiTKt1lZJGHclyyGyrNLdnso0zs1Y011H9ybuNTyeC_Ng0uPscNyiU-KxRgBPWRb5KK-de35Cqgp60c7LQMRAhH8x-Ax4_ExsTKSY3megScGMxI77Y3jzCpDEMS6Q8X7xxMT16w8DkYy-66DXFpRsSNdLeI2-fR3pZtZJvcADgFCABKqHCbwjEtyM23MNmJaY2bLP0reJnM8AghKpEpC8Rxvn_WFibvgjcClBrZUFX219iYJpmBLOnSqWwX35-joqLFpD_mVE_qPH0SPWxY1fJz5UMT8NIoZocJU_AT0AMqpOWnk6vZdR2Yfz1vKGynqJ1AotlDy4U0BBc4rwf6CWQgrUIc1Ne4v-5j0NBIfj6hAl77Lfw7ktkMrGtvfYXcSxDaHoRSWalBjvLc4YIOMEST-i1LsMekIosRZL-cu83B8QU0YmzWH9SqMtubD1AoeoqTzCJ28JllaMVZfbzSuRfDMzqXe3mKsUJ7FWf-1DBVBAzdOWzsOlOvBbK2I9AaeZPll5slJ75Q7DskGJDih0mqwNN4hJ--S1_kk4Pc5xcZC-J5OTTMNg1fYRmAj1NbEve_lmmJCY-eWIWOt9Kq_Fdp8hlWwH4dHv74ozcS7kd66Ww72SL6sgb3tb1yGAmodYlBkjNpE98QhJCzsk6reldeN-PYiLfxmuVy6YYENx4tg_21Dx7kQ2qJizqsu43LSJnrbHc.y7fKslu6r90SvfAUx-oKqg";
        String _abck = "B539EBD756DB91E1A8E4DC0CA223F534~-1~YAAQrW2bG/x4diiBAQAArLPvOAgQcM/fhRQGE0QWLY3zBGFJxheXzCxNXD+xrrcEWCPnZB8Kz6e0oTVGIQC5YsWpzY2mQahfXhRw3Isf+YcsjTICdh5Qjz360BwBCW1y2CcPg9Upbq/2fyKdO6tWD1eLNXUJPgSrWPBuTEytQNpWmZAMvoYoNGx84WFFmHiiNa65e2cMXIQlOlk6uNfdm2jcH/ZHU+2RCBAxro5esfN86zmWdCVfjExtNQxH81mqsEEc9aZcegVkxWxYyiZe1ALEn95/iUh+C3cReo7CbmOwIGrI2xLsS4wQh6slvJB+zWyfO7GCdRSTmhAPEPvqhMxBbfFfFIiw4suOdi4QSJspTbvcfw3gmvET5xluevb4/6ZqMKjWquRckmXZIAfqpD+gmwugFNvtiHOb/ke/VvhJ4AwMLbSODqexeC/cfp2f2MhbsiuV2tg80kVbuWMfYQkvjQCMtYqgrpIXg9o=~-1~-1~-1";
        headerMap.put("accept", "*/*");
        headerMap.put("accept-encoding", "gzip, deflate, br");
        headerMap.put("accept-language", "zh-CN,zh;q=0.9");
        headerMap.put("cache-control", "no-cache");
        headerMap.put("Cookie", "CONSENTMGR=ts:1654440739230%7Cconsent:true; tiSessionID=0181345a41a5001787358bf4f5530506f001606700978; " +
                "_ga=GA1.3.957253377.1654440740; _gid=GA1.3.356447862.1654440740; user_pref_language=\"zh-CN\"; user_pref_currency=\"USD\"; " +
                "_fbp=fb.2.1654440742872.1061395031; _gcl_au=1.1.1784990448.1654440743; __adroll_fpc=545347ec2f339635967d613e81cfb277-1654440743149; " +
                "ELOQUA=GUID=CC6F22A3A83841AFB9B818ECBE0A96B6; _sg_b_n=1654440745767; last-domain=www.ti.com.cn; " +
                "ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f103.0.0.0%20Safari%2f537.36; " +
                "login-check=null; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; " +
                "bm_mi=F194DB2FDF54E07CC15A04A3DF04C070~YAAQ5G+bG04X4wCBAQAAMVNfOBBEU3DEG9Ty+m0Lzf5792wWnnf6aOFlj9IDKd8O2vRdl7c7cCyvDc/YLwfdpDXfHRuSJhH6MA+6+Uu+fAGaz9uhX63VDjkz4dDiYKGnWZS7KioC0iFOBS1iDJqeZXGkXKer5no94JSNMtpu9o7SLuOaDVFGSUV2tpQBpju0vnliDbE0wQa4FFwess2T1NjpcKOOsqX05jBCJ03a6gcmkLQfiYHU8jiLjj1ekjsga9ym7K57Y+tNsSVPN7+QWsMNWZMJgXR2Mpc4V1rjua+ZRapwO2baaPxSpmcSLIUZJlzufiCGra6IDt41olGVngI=~1; " +
                "__ar_v4=QFXRHQEHOJDMLHSLFIWCLO%3A20220605%3A16%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20220605%3A16%7CG3YHLXUICZC3XKDXYVVLO4%3A20220605%3A16; " +
                "_sg_b_v=10%3B55775%3B1654508645; bm_sv=224AEDDC29924F8280532D8AC7011B4E~YAAQrW2bG0YScSiBAQAADoxmOBAOp3CuCgAJJDvJBgh6N1Kg8S0dzUhNqt5bA37QfUZl0+SlZ/7GGQ0Q3pHgQZ2yoO8DteZpN1iT3KXmpq4aJa6TMTLmAH8lHHDPwwYKhYTKmR0fcMvej1LJGi4+wUMJ4Ic5CtuNjIE/+vUIcoXtuey9Yi0unCifJvuB4MBIp6GW34WjUDlkc0VbMIgyg8p9yc/P1YbmaWxPkyQzq8DZmZZp9H534jgQirCOwjuC~1; " +
                "bm_sz=DE5FF8C9D0D62BF313DBD8B6F14A5B24~YAAQrW2bG/AUcSiBAQAAKwNnOBASxtSsdzBspwVUsVAZyYGbrJG2iQvriuRdkz2jxxt1M3A1Dpa01CIGDS7emxdaWQG/EzeHq3/BnXP0r4oo0Kq8NfildBl6G+AbvbD2Alu2CQRa1Lj0BCk/aSYOWbBZXxFCnix2+jxuU7tp5EgfWU1Spod9i1UDoVb6jzr4HfEU4+b4ykETJVQnmtcTLiqlH2pMGg0IDZCo8TXmKYyfNdDpw3HmG1jVtWJ2DrTQ8mFbsx0CHJv/UFpzhy5YUMh+J2gYqihG9AMAkT1Ss41cA7KyS0r6OkeWxS3sR+9wTuSJpW0P38OqsKmJeMG8vCZ8Y0tBksZmOtWmHlZIqPsI646G3PkrUKJ0J5nbMFZx6G6lSWzA4qgOXFgp2w==~3686711~4277296; " +
                "user_pref_givenName=\"%E5%B9%B3\"; user_pref_givenNameLocalLanguage=\"ping\"; ABTasty=uid=q7maddze47w6zh08&fst=1654440740850&pst=1654496502857&cst=1654506756113&ns=6&pvt=39&pvis=14&th=686831.851794.20.13.4.1.1654444604966.1654508699367.1; " +
                "da_lid=6F461C239A73EA094182BB99F7793CB67A|0|0|0; auth_session=" + auth_session + "; " +
                "userType=Anonymous; gpn=LM324-gpn; tipage=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324%2Fproduct%20folder-lm324-cn; tipageshort=product%20folder-lm324-cn; ticontent=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; ga_page_cookie=product%20folder-lm324-cn; ga_content_cookie=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; _abck=" + _abck + "; " +
                "ti_rid=1ebe120b; ti_bm=Unknown%20Bot%20(3F79075490CF113B9040484F78EC254E)%3amonitor%3a%3aJavaScript%20Fingerprint%20Not%20Received%20(BETA)%3a%3a");
        headerMap.put("pragma", "no-cache");
        headerMap.put("referer", "https://www.ti.com.cn/product/zh-cn/LM324");
        headerMap.put("sec-ch-ua", "\".Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"103\", \"Chromium\";v=\"103\"");
        headerMap.put("sec-ch-ua-mobile", " ?0");
        headerMap.put("sec-ch-ua-platform", "\"Windows\"");
        headerMap.put("sec-fetch-dest", "empty");
        headerMap.put("sec-fetch-mode", "cors");
        headerMap.put("sec-fetch-site", "same-origin");
        headerMap.put("traceparent", "00-6cdcb2c2cb82e7bbd5cf904a5c5ebe40-634fc37de6396b78-01");
        headerMap.put("tracestate", "1565136@nr=0-1-1720594-1309198578-634fc37de6396b78----1654511843890");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");
        String s = HttpURLConnectionUtil.httpGet(url, headerMap, null);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }


    @Test
    public void addtocart() throws Exception {
        String chipModel = "LM324";
        String url = "https://www.ti.com.cn/occservices/v2/ti/addtocart";
        Map<String, String> headerMap = new HashMap<>();
//         :authority: www.ti.com.cn
// :method: GET
//         :path: /productmodel/gpn/LM324/tistoresegmented
// :scheme: https
//         accept: */*
// accept-encoding: gzip, deflate, br
// accept-language: zh-CN,zh;q=0.9
// cache-control: no-cache
// cookie: CONSENTMGR=ts:1654440739230%7Cconsent:true; tiSessionID=0181345a41a5001787358bf4f5530506f001606700978; _ga=GA1.3.957253377.1654440740; _gid=GA1.3.356447862.1654440740; user_pref_language="zh-CN"; user_pref_currency="USD"; _fbp=fb.2.1654440742872.1061395031; _gcl_au=1.1.1784990448.1654440743; __adroll_fpc=545347ec2f339635967d613e81cfb277-1654440743149; ELOQUA=GUID=CC6F22A3A83841AFB9B818ECBE0A96B6; _sg_b_n=1654440745767; last-domain=www.ti.com.cn; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f103.0.0.0%20Safari%2f537.36; login-check=null; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; bm_mi=F194DB2FDF54E07CC15A04A3DF04C070~YAAQ5G+bG04X4wCBAQAAMVNfOBBEU3DEG9Ty+m0Lzf5792wWnnf6aOFlj9IDKd8O2vRdl7c7cCyvDc/YLwfdpDXfHRuSJhH6MA+6+Uu+fAGaz9uhX63VDjkz4dDiYKGnWZS7KioC0iFOBS1iDJqeZXGkXKer5no94JSNMtpu9o7SLuOaDVFGSUV2tpQBpju0vnliDbE0wQa4FFwess2T1NjpcKOOsqX05jBCJ03a6gcmkLQfiYHU8jiLjj1ekjsga9ym7K57Y+tNsSVPN7+QWsMNWZMJgXR2Mpc4V1rjua+ZRapwO2baaPxSpmcSLIUZJlzufiCGra6IDt41olGVngI=~1; __ar_v4=QFXRHQEHOJDMLHSLFIWCLO%3A20220605%3A16%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20220605%3A16%7CG3YHLXUICZC3XKDXYVVLO4%3A20220605%3A16; _sg_b_v=10%3B55775%3B1654508645; bm_sv=224AEDDC29924F8280532D8AC7011B4E~YAAQrW2bG0YScSiBAQAADoxmOBAOp3CuCgAJJDvJBgh6N1Kg8S0dzUhNqt5bA37QfUZl0+SlZ/7GGQ0Q3pHgQZ2yoO8DteZpN1iT3KXmpq4aJa6TMTLmAH8lHHDPwwYKhYTKmR0fcMvej1LJGi4+wUMJ4Ic5CtuNjIE/+vUIcoXtuey9Yi0unCifJvuB4MBIp6GW34WjUDlkc0VbMIgyg8p9yc/P1YbmaWxPkyQzq8DZmZZp9H534jgQirCOwjuC~1; bm_sz=DE5FF8C9D0D62BF313DBD8B6F14A5B24~YAAQrW2bG/AUcSiBAQAAKwNnOBASxtSsdzBspwVUsVAZyYGbrJG2iQvriuRdkz2jxxt1M3A1Dpa01CIGDS7emxdaWQG/EzeHq3/BnXP0r4oo0Kq8NfildBl6G+AbvbD2Alu2CQRa1Lj0BCk/aSYOWbBZXxFCnix2+jxuU7tp5EgfWU1Spod9i1UDoVb6jzr4HfEU4+b4ykETJVQnmtcTLiqlH2pMGg0IDZCo8TXmKYyfNdDpw3HmG1jVtWJ2DrTQ8mFbsx0CHJv/UFpzhy5YUMh+J2gYqihG9AMAkT1Ss41cA7KyS0r6OkeWxS3sR+9wTuSJpW0P38OqsKmJeMG8vCZ8Y0tBksZmOtWmHlZIqPsI646G3PkrUKJ0J5nbMFZx6G6lSWzA4qgOXFgp2w==~3686711~4277296; user_pref_givenName="%E5%B9%B3"; user_pref_givenNameLocalLanguage="ping"; ABTasty=uid=q7maddze47w6zh08&fst=1654440740850&pst=1654496502857&cst=1654506756113&ns=6&pvt=39&pvis=14&th=686831.851794.20.13.4.1.1654444604966.1654508699367.1; da_lid=6F461C239A73EA094182BB99F7793CB67A|0|0|0; auth_session=yq4vhV0uWke6Ut70.uAVbaq6zn7fthWCRYIjXBJ9JLoYTEKit7iqefBb1ZJOGfaKX-32AL5M8bojWQEaFjB9DRNZSWJqsp9QLDpvjRc9NWkGdvngavytZAh3z7AKDNXSulbGP156hWbZ1aRnHq39VcaGZLixoDYHRK6rOQicclUSpDeBuP5QNFH2-MB_zcNWXh_d1GYKZPopkjw_fFjx9RqnM_7eKWz2dPHdo-7NRH4MzVFORRRDCzoWuskTc96Asddq8Z00LAedN5Q0FOdDByZ9L3Kg90owFzIE1phxvrhZYaWZCozM5kaEtdgsBTSz_Qg7Vj_Amad1pScqjrcaEdusTyl-AYKVlDtJ9_MCLoXsjgOqEGV6BuEy4nFe6fidC_GFb2XGgWfKY7qSqfzOffmx4pfWlC0djVSqikJwV-lCLUD2Xx_I_v47-RqCfg2HztJ1gYxyuDNBPPq6VJzDqX_Bd-ImqzQLBya3vNZwz-ezrax7HhzhMKsgjI_q_qossJnIgTZ6WTRB0C9mIM-lTZt0JKOPqh2_uyd4Wrrq6NRW2kK28eFQVzlKP-Qyk6UwFF0_LSsFOjNbvBhTcaJGbJC28DWuaZlGJCAXTj9asHgDGdHzLi7Bc2oi8AHiLxmrcJmhbmHX4MWiH7iAHZ1fpbYi3f0dcFoFqjQjgLLg6WzeegODPoGl4t-U9kyBkygCTjIojZECLGB_q83HWt0HFdSgIJ4z8M_CCDPBEH9q-v5pRSeuelrLgecnMiSXPc4605j-owabKAH8sgP5OdQe-gradfVl-052Yju-GjM2yiZXhkR_UB2OpUK37tAkjy899TzDO6I31B97bD5AahVfZHIL-sp0Ggt09E0h-CFphhOcNYHgLBA23JUaVzZ11sJb5g1t53COxLuJaSo-wtZAkyFNpFJuJv0a6_u5gk5Bv9QWKfFka8nX5e2G6ttv4pnAahtgj4VFl1qDUYSNcnocvjhqhAgk01t3QLnRnb_GCHnBIwIanHDoYX2L_MKV7IqZ1bogx0vDgfn-SL_zBOd249fZdijW2g5jldS9Et_LPIodnsIe0mTD67fZo_5IVCtmfO1iKCDF02YVxJuq4j9Ge1ccUZIBXeJclreBcffkhPpzEQtou-jnhRHuzM352QP-r4kKO5bRO_TElsd0Qh5hVeSDBcN0ds3zw47Aq8qnylNkAD5bGM7Dpb18BTM5cUCNucq8jRX9fQ-1HBrjBTbEEjuj3AcWfaUXN8Q8V5U4oxcgHT_5qyOJUsLMUH1pGISDKPjNV04zb7h5hw3fzUFqeRj_wGV9FDc24-qqnqUBeWv-NtsBDpFXEjnhY-3UYow8FfIt-ShNozaQWLA8kgwGcTHgifcW50EhAOZMru5dcPlwWBqCu7SAyBrsJ8PY3d0ozFjw2jGZ3i6o8WfYIkcHnms3NyxZWq4R9CiwRvDH4_3mGuS6jeaxJuEnK0eAGUvrEBO-tgLPAjqHuAo3OC5HasktgCj9jSGk-wiOnUvM8D3DpsAD6rIsu9ge_k7prjmyfLiOtQgDNbnKcBA8yXiKxOeatz1xGaUvmA-Ub25UcoCjn3jcQjKe9Gia0w3uhzLXmdN8mLsXs56TZK6KcaUC53osDUNeweaOer2TZ2STQE9xCoK3MCrfqWLsggqQb0Tmy4UcyjkXi-9m-5YOM5uNJIj7YkvYpfuRyzLhjcnjX7DzyOm5ZTDrjHHkx5JVHFLOj2vYv46uAJPtL.bJpQXnVXeM96BraYHcSmRg; ak_bmsc=3643DFBF76141B2A6A349D949BE26A82~000000000000000000000000000000~YAAQ5G+bG9dB7gCBAQAAzyuXOBBtDKMYUwzaqFAUrSAWKy9aIr+lTj1z/PtS+eJAijx9fLQpeaEi446XVh5OzGFlbFid/B14xZleBEJgG4Lsmly3Vcrm/zHlu7wKfaWunpdJB5FHfUXca6zror6SctQbpxUrUs0z8UCl8VA8Q2I37fi6te7B8QVwbU5Xi3lhw3ygzR/3KbPTNBG+5oP7oAuSykAxLz3SoJOcSE9AztnH25S2rVeQFqc8Q8T4qVRVtH2xnMuBSaRfL2eZtjEo5XXxK62g+p9nwazj291ZK7C3DUgzlJFQPwYr3An02TbXQ+HmbQd/AgPUCur+Q81RgtVuZa4KGrBugNPwmwxvUCKqlUkV6IhwW7Y4lEGxEoxV+NhuTV3j0Yx9yTbkpWowzDoA0sMI4uBkUXoTgN4f3D5Ys/QEa7PlaUQoEBS9AD6r1VlAdSzXd2XrCtOcJ/E2NoqSDF8/yTfVDOKHq6Ej1hTb+AoKhKYJ8SxxCD3fbo4=; utag_main=free_trial:false$channel:organic%3Bexp-1657100139568$ctimestamp:Mon%20Jun%2006%202022%2017%3A35%3A39%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)%3Bexp-1657100139568$_st:1654513642851$v_id:0181345a41a5001787358bf4f5530506f001606700978$_sn:7$_ss:1$dc_visit:6$_pn:1%3Bexp-session$ses_id:1654511842851%3Bexp-session; userType=Anonymous; gpn=LM324-gpn; tipage=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324%2Fproduct%20folder-lm324-cn; tipageshort=product%20folder-lm324-cn; ticontent=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; ga_page_cookie=product%20folder-lm324-cn; ga_content_cookie=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; _abck=B539EBD756DB91E1A8E4DC0CA223F534~-1~YAAQ5G+bG4pC7gCBAQAAPDOXOAiR/LgBv8nhdn9Sujsb+Mi6DDP01tAMrp4zW7Zn3Uv66tFLnBDDV2JuF0Y2TnJOY4Mvhf/BXqFoZ50hcU1PNca9bp/27TWVdRFuvgC+LPPPk9zhjQ3cF8AeKjiGPUCZCmU8/0Rp/UuFR0I73EgGzV83re4K7tv4xfXAHHB0mz2gxCMQgqGQEhOQ7R9dE9yxspUzPo71hfntl5DUOPmoZmfBPibdNo6Sha7x7YYyODmdoY9dhMB30LnJSVnIAHF2mSnc/xAgyGQAZ0CM3q4CHErZ1Fckek88aMIEBpiYOQWDRDZ2J8lUuo0elj5uC5/iyZZ9ldjutv1Ppos0vB7+tSRXmq31okPV2DWhT30OEa+90MvuRo4/IUyjK0BhMoZYLXMkRvs136UMfaPS5MOF5IacXycKV9Up4M+YnJqPyHvgMzoHXhq2V8FcYwoxnSUDdMD39noxmEoIg9aeWP3WMtY03rE/YE4yh+8=~-1~-1~-1; ti_rid=1ebe120b; ti_bm=Unknown%20Bot%20(3F79075490CF113B9040484F78EC254E)%3amonitor%3a%3aJavaScript%20Fingerprint%20Not%20Received%20(BETA)%3a%3a
// newrelic: eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjE3MjA1OTQiLCJhcCI6IjEzMDkxOTg1NzgiLCJpZCI6IjYzNGZjMzdkZTYzOTZiNzgiLCJ0ciI6IjZjZGNiMmMyY2I4MmU3YmJkNWNmOTA0YTVjNWViZTQwIiwidGkiOjE2NTQ1MTE4NDM4OTAsInRrIjoiMTU2NTEzNiJ9fQ==
// pragma: no-cache
// referer: https://www.ti.com.cn/product/zh-cn/LM324
// sec-ch-ua: ".Not/A)Brand";v="99", "Google Chrome";v="103", "Chromium";v="103"
// sec-ch-ua-mobile: ?0
// sec-ch-ua-platform: "Windows"
// sec-fetch-dest: empty
// sec-fetch-mode: cors
// sec-fetch-site: same-origin
// traceparent: 00-6cdcb2c2cb82e7bbd5cf904a5c5ebe40-634fc37de6396b78-01
// tracestate: 1565136@nr=0-1-1720594-1309198578-634fc37de6396b78----1654511843890
// user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36
//         headerMap.put(":authority", "www.ti.com.cn");
        // headerMap.put(":method", "GET");
        // headerMap.put(":path", "/productmodel/gpn/"+chipModel+"/tistoresegmented");
        // headerMap.put(":scheme", "https");
        String auth_session = "qBYbibgrZ8J6ZzLf.AvPBOS-QqIXSmEv-oySXkgzMmrqSBkP9dGEof65MDixOWyGUjSruY0WAShzB5fg1kWiPjmwOImZL-71y2oGoBWkqwK7bt31MK6IEJEMkwzVdWz39AbYgZE0vgtWzX34tLxnSz3tiMTIJDL0ebLaQoWdCv4Il0ApfcoH7sgUqyclwJ_laE5mGGjEof0zjRs2y0CtRKawHai0atUNIE5PpPTW0rnr-4nrt0kuFUxNmJdyBH7vyvz_LJOVdvNmot2jUMQ0Gp9RW188YFazfbx4fO9V_W_LOFygUAD9RPrU3eGLks0gv_niP6ynC4tjl5wkdscdk7z5EqpJKIiCyskwQQ9qyXKKH28I8WTRM5fcWBm6KhmPQFcGqbxq4gG8Bl-lWV7cPKI_gekIfHPUwCxyRPGyQbK9nQ9G9tDptYeutkPqWTQzafTfghIRNNlnn_vdWeG1QusomQy8bjnSAg88W3JcVzodi5HdwYyFvROtOkl0aghNaQy0OkjSlnV3YsjlkGCxfEveiB-C9RSI6CugzBaOMfaHJYltCeFYB1LuTK1I86xDoCA1OVV0Nbi-3A2NRX4_jHYOIVuRF3hE7yw91AAG4p6NQzBn1EMKzmiuZ96HtSwG4CuSmWOKHMrQh7qSfbntEPIxGIFrjZHvMsEZ_af3_njl1yuqktKkToZf5dCwIygbMGxxxDkUFM3FG8umo5xPICiB9BbfTRhNmCVW9PLW66CN4Rcig3RLcO8qh7VFN7nM-vxHbAh0nYmm7Cmzq-78qafc4EEYOl15JuOyDgBssz0OTxZQWquaEcElYoqtOfKIJt9CTAU5vff9O5-n-SrN1YHskxn-QHVOj3yFssRZR9pOo_Xaep8erChknzELpGiVvp-b5jGe5J_p5_YWztApbSsFQPb7Lc0QtWxs2tZHzRsIRYbny020G4QZJ1lrO2T2jy-XgU2D-Q9T3GcNDQ7I_izYbdABLmz2MDkILwO4KQgS3iaigzig6rDLc1sqn54HjfhVTt3yiTKt1lZJGHclyyGyrNLdnso0zs1Y011H9ybuNTyeC_Ng0uPscNyiU-KxRgBPWRb5KK-de35Cqgp60c7LQMRAhH8x-Ax4_ExsTKSY3megScGMxI77Y3jzCpDEMS6Q8X7xxMT16w8DkYy-66DXFpRsSNdLeI2-fR3pZtZJvcADgFCABKqHCbwjEtyM23MNmJaY2bLP0reJnM8AghKpEpC8Rxvn_WFibvgjcClBrZUFX219iYJpmBLOnSqWwX35-joqLFpD_mVE_qPH0SPWxY1fJz5UMT8NIoZocJU_AT0AMqpOWnk6vZdR2Yfz1vKGynqJ1AotlDy4U0BBc4rwf6CWQgrUIc1Ne4v-5j0NBIfj6hAl77Lfw7ktkMrGtvfYXcSxDaHoRSWalBjvLc4YIOMEST-i1LsMekIosRZL-cu83B8QU0YmzWH9SqMtubD1AoeoqTzCJ28JllaMVZfbzSuRfDMzqXe3mKsUJ7FWf-1DBVBAzdOWzsOlOvBbK2I9AaeZPll5slJ75Q7DskGJDih0mqwNN4hJ--S1_kk4Pc5xcZC-J5OTTMNg1fYRmAj1NbEve_lmmJCY-eWIWOt9Kq_Fdp8hlWwH4dHv74ozcS7kd66Ww72SL6sgb3tb1yGAmodYlBkjNpE98QhJCzsk6reldeN-PYiLfxmuVy6YYENx4tg_21Dx7kQ2qJizqsu43LSJnrbHc.y7fKslu6r90SvfAUx-oKqg";
        String _abck = "B539EBD756DB91E1A8E4DC0CA223F534~-1~YAAQrW2bG/x4diiBAQAArLPvOAgQcM/fhRQGE0QWLY3zBGFJxheXzCxNXD+xrrcEWCPnZB8Kz6e0oTVGIQC5YsWpzY2mQahfXhRw3Isf+YcsjTICdh5Qjz360BwBCW1y2CcPg9Upbq/2fyKdO6tWD1eLNXUJPgSrWPBuTEytQNpWmZAMvoYoNGx84WFFmHiiNa65e2cMXIQlOlk6uNfdm2jcH/ZHU+2RCBAxro5esfN86zmWdCVfjExtNQxH81mqsEEc9aZcegVkxWxYyiZe1ALEn95/iUh+C3cReo7CbmOwIGrI2xLsS4wQh6slvJB+zWyfO7GCdRSTmhAPEPvqhMxBbfFfFIiw4suOdi4QSJspTbvcfw3gmvET5xluevb4/6ZqMKjWquRckmXZIAfqpD+gmwugFNvtiHOb/ke/VvhJ4AwMLbSODqexeC/cfp2f2MhbsiuV2tg80kVbuWMfYQkvjQCMtYqgrpIXg9o=~-1~-1~-1";
        headerMap.put("accept", "*/*");
        headerMap.put("accept-encoding", "gzip, deflate, br");
        headerMap.put("accept-language", "zh-CN,zh;q=0.9");
        headerMap.put("cache-control", "no-cache");
        headerMap.put("Cookie", "CONSENTMGR=ts:1654440739230%7Cconsent:true; tiSessionID=0181345a41a5001787358bf4f5530506f001606700978; " +
                "_ga=GA1.3.957253377.1654440740; _gid=GA1.3.356447862.1654440740; user_pref_language=\"zh-CN\"; user_pref_currency=\"USD\"; " +
                "_fbp=fb.2.1654440742872.1061395031; _gcl_au=1.1.1784990448.1654440743; __adroll_fpc=545347ec2f339635967d613e81cfb277-1654440743149; " +
                "ELOQUA=GUID=CC6F22A3A83841AFB9B818ECBE0A96B6; _sg_b_n=1654440745767; last-domain=www.ti.com.cn; " +
                "ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f103.0.0.0%20Safari%2f537.36; " +
                "login-check=null; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=113.92.34.6; " +
                "bm_mi=F194DB2FDF54E07CC15A04A3DF04C070~YAAQ5G+bG04X4wCBAQAAMVNfOBBEU3DEG9Ty+m0Lzf5792wWnnf6aOFlj9IDKd8O2vRdl7c7cCyvDc/YLwfdpDXfHRuSJhH6MA+6+Uu+fAGaz9uhX63VDjkz4dDiYKGnWZS7KioC0iFOBS1iDJqeZXGkXKer5no94JSNMtpu9o7SLuOaDVFGSUV2tpQBpju0vnliDbE0wQa4FFwess2T1NjpcKOOsqX05jBCJ03a6gcmkLQfiYHU8jiLjj1ekjsga9ym7K57Y+tNsSVPN7+QWsMNWZMJgXR2Mpc4V1rjua+ZRapwO2baaPxSpmcSLIUZJlzufiCGra6IDt41olGVngI=~1; " +
                "__ar_v4=QFXRHQEHOJDMLHSLFIWCLO%3A20220605%3A16%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20220605%3A16%7CG3YHLXUICZC3XKDXYVVLO4%3A20220605%3A16; " +
                "_sg_b_v=10%3B55775%3B1654508645; bm_sv=224AEDDC29924F8280532D8AC7011B4E~YAAQrW2bG0YScSiBAQAADoxmOBAOp3CuCgAJJDvJBgh6N1Kg8S0dzUhNqt5bA37QfUZl0+SlZ/7GGQ0Q3pHgQZ2yoO8DteZpN1iT3KXmpq4aJa6TMTLmAH8lHHDPwwYKhYTKmR0fcMvej1LJGi4+wUMJ4Ic5CtuNjIE/+vUIcoXtuey9Yi0unCifJvuB4MBIp6GW34WjUDlkc0VbMIgyg8p9yc/P1YbmaWxPkyQzq8DZmZZp9H534jgQirCOwjuC~1; " +
                "bm_sz=DE5FF8C9D0D62BF313DBD8B6F14A5B24~YAAQrW2bG/AUcSiBAQAAKwNnOBASxtSsdzBspwVUsVAZyYGbrJG2iQvriuRdkz2jxxt1M3A1Dpa01CIGDS7emxdaWQG/EzeHq3/BnXP0r4oo0Kq8NfildBl6G+AbvbD2Alu2CQRa1Lj0BCk/aSYOWbBZXxFCnix2+jxuU7tp5EgfWU1Spod9i1UDoVb6jzr4HfEU4+b4ykETJVQnmtcTLiqlH2pMGg0IDZCo8TXmKYyfNdDpw3HmG1jVtWJ2DrTQ8mFbsx0CHJv/UFpzhy5YUMh+J2gYqihG9AMAkT1Ss41cA7KyS0r6OkeWxS3sR+9wTuSJpW0P38OqsKmJeMG8vCZ8Y0tBksZmOtWmHlZIqPsI646G3PkrUKJ0J5nbMFZx6G6lSWzA4qgOXFgp2w==~3686711~4277296; " +
                "user_pref_givenName=\"%E5%B9%B3\"; user_pref_givenNameLocalLanguage=\"ping\"; ABTasty=uid=q7maddze47w6zh08&fst=1654440740850&pst=1654496502857&cst=1654506756113&ns=6&pvt=39&pvis=14&th=686831.851794.20.13.4.1.1654444604966.1654508699367.1; " +
                "da_lid=6F461C239A73EA094182BB99F7793CB67A|0|0|0; auth_session=" + auth_session + "; " +
                "userType=Anonymous; gpn=LM324-gpn; tipage=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324%2Fproduct%20folder-lm324-cn; tipageshort=product%20folder-lm324-cn; ticontent=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; ga_page_cookie=product%20folder-lm324-cn; ga_content_cookie=%2Fanalog%20%26%20mixed-signal%2Famplifiers%2Foperational%20amplifiers%20(op%20amps)%2Fgeneral-purpose%20op%20amps%2Flm324; _abck=" + _abck + "; " +
                "ti_rid=1ebe120b; ti_bm=Unknown%20Bot%20(3F79075490CF113B9040484F78EC254E)%3amonitor%3a%3aJavaScript%20Fingerprint%20Not%20Received%20(BETA)%3a%3a");
        headerMap.put("pragma", "no-cache");
        headerMap.put("referer", "https://www.ti.com.cn/product/zh-cn/LM324");
        headerMap.put("sec-ch-ua", "\".Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"103\", \"Chromium\";v=\"103\"");
        headerMap.put("sec-ch-ua-mobile", " ?0");
        headerMap.put("sec-ch-ua-platform", "\"Windows\"");
        headerMap.put("sec-fetch-dest", "empty");
        headerMap.put("sec-fetch-mode", "cors");
        headerMap.put("sec-fetch-site", "same-origin");
        headerMap.put("traceparent", "00-6cdcb2c2cb82e7bbd5cf904a5c5ebe40-634fc37de6396b78-01");
        headerMap.put("tracestate", "1565136@nr=0-1-1720594-1309198578-634fc37de6396b78----1654511843890");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");
        String s = HttpURLConnectionUtil.httpGet(url, headerMap, null);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }


    @Test
    public void Test() {
        LOG.info("" + tableSizeFor(31));
    }

    int tableSizeFor(int var0) {
        int var1 = var0 - 1;
        var1 |= var1 >>> 1;
        var1 |= var1 >>> 2;
        var1 |= var1 >>> 4;
        var1 |= var1 >>> 8;
        var1 |= var1 >>> 16;
        return var1 < 0 ? 1 : (var1 >= 1073741824 ? 1073741824 : var1 + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();

        Map<Character, char[]> map = new HashMap<>();
        map.put('0', new char[] {' '});
        map.put('1', new char[] {',', '.'});
        map.put('2', new char[] {'a', 'b', 'c'});
        map.put('3', new char[] {'d', 'e', 'f'});
        map.put('4', new char[] {'g', 'h', 'i'});
        map.put('5', new char[] {'j', 'k', 'l'});
        map.put('6', new char[] {'m', 'n', 'o'});
        map.put('7', new char[] {'p', 'q', 'r', 's'});
        map.put('8', new char[] {'t', 'u', 'v'});
        map.put('9', new char[] {'w', 'x', 'y', 'z'});

        boolean numberMode = true;
        int index = 0;
        char curChar = '\0';
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '/') {
                if (index != 0) {
                    char[] chars = map.get(curChar);
                    buffer.deleteCharAt(buffer.length() - 1);
                    buffer.append(chars[index % chars.length]);
                    curChar = '\0';
                    index = 0;
                }
                continue;
            }
            if (c == '#') {
                numberMode = !numberMode;
                if (index != 0) {
                    char[] chars = map.get(curChar);
                    buffer.deleteCharAt(buffer.length() - 1);
                    buffer.append(chars[index % chars.length]);
                    curChar = '\0';
                    index = 0;
                }
                continue;
            }
            if (numberMode) {
                buffer.append(c);
                continue;
            }

            // none number mode
            if (curChar != '\0' && curChar != c) {
                if (index != 0) {
                    char[] chars = map.get(curChar);
                    buffer.deleteCharAt(buffer.length() - 1);
                    buffer.append(chars[index % chars.length]);
                    curChar = '\0';
                    index = 0;
                } else {
                    curChar = c;
                    buffer.append(map.get(c)[0]);
                }
            }
            index++;
        }
        System.out.println(buffer);
    }

    private static int getlengthOfLastChar(StringBuffer buffer, char target) {
        int result = 0;
        for (int i = buffer.length() - 1; i >= 0; --i) {
            if (buffer.charAt(i) == target) {
                result ++;
            } else {
                return result;
            }
        }
        return result;
    }

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     String memory = sc.nextLine();
    //     String needs = sc.nextLine();
    //     sc.close();
    //
    //     int max = 0;
    //     Map<Integer, Integer> map = new HashMap<>();
    //     String[] array = memory.split(",");
    //     for (int i = 0; i < array.length; ++i) {
    //         String[] arr = array[i].split(":");
    //         int m = Integer.parseInt(arr[0]);
    //         map.put(m, Integer.parseInt(arr[1]));
    //         if(m > max) {
    //             max = m;
    //         }
    //     }
    //
    //     List<Boolean> result = new ArrayList<>();
    //     String[] needArray = needs.split(",");
    //     for (int i = 0; i < needArray.length; ++i) {
    //         int need = getMinNumOf2Pow(Integer.parseInt(needArray[i]));
    //         Integer count = map.get(need);
    //         if (count != null && count > 0) {
    //             map.put(need, count - 1);
    //             result.add(true);
    //         } else {
    //             checkMemoryAvailable(max, map, need, result);
    //         }
    //     }
    //     for (int i = 0; i < result.size(); ++i) {
    //         System.out.print(result.get(i));
    //         if(i == result.size() - 1) {
    //             System.out.println();
    //         } else {
    //             System.out.print(",");
    //         }
    //
    //     }
    // }

    private static void checkMemoryAvailable(int max, Map<Integer, Integer> map, int need, List<Boolean> result) {
        while(need <= max) {
            Integer count = map.get(need);
            if (count != null && count > 0) {
                map.put(need, count - 1);
                result.add(true);
                return;
            }
            need *= 2;
        }
        result.add(false);
    }

    private static int getMinNumOf2Pow(int var0) {
        int var1 = var0 - 1;
        var1 |= var1 >>> 1;
        var1 |= var1 >>> 2;
        var1 |= var1 >>> 4;
        var1 |= var1 >>> 8;
        var1 |= var1 >>> 16;
        return var1 < 0 ? 1 : (var1 >= 1073741824 ? 1073741824 : var1 + 1);
    }

}
