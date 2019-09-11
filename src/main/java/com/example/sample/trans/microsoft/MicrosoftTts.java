package com.example.sample.trans.microsoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;

// https://github.com/Azure-Samples/Cognitive-Speech-TTS/tree/master/Samples-Http/Java/TTSSample/
public class MicrosoftTts {

    private static Logger logger = LoggerFactory.getLogger(MicrosoftTts.class);

    public static void main(String[] args) throws Exception {
        String accessToken = getAccessToken();
        logger.info(">>>>> ACCESS_TOKEN: [{}]", accessToken);

        String text = "This is a demo to call microsoft text to speech service in java.";

        String format = "audio-16khz-64kbitrate-mono-mp3";
        String language = "en-US";

        // String gender = "Female";
        String gender = "Male";

        // Short name for "Microsoft Server Speech Text to Speech Voice (en-US, Guy24KRUS)"
        String voiceName = "en-US-Guy24kRUS";

        byte[] audioBuffer = tts(text, format, language, gender, voiceName, accessToken);

        // write the pcm data to the file
        String outputWave = ".\\output.mp3";
        File outputAudio = new File(outputWave);
        FileOutputStream fos = new FileOutputStream(outputAudio);
        fos.write(audioBuffer);
        fos.flush();
        fos.close();
    }

    private static String getAccessToken() throws Exception {
        String uri = "https://westus.api.cognitive.microsoft.com/sts/v1.0/issueToken";
        URL url = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");

        byte[] bytes = new byte[0];
        connection.setRequestProperty("content-length", String.valueOf(bytes.length));
        String subscriptionKey = "";
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
        connection.connect();

        OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        connection.disconnect();

        return builder.toString();
    }

    private static byte[] tts(String text, String format, String language,
                              String gender, String voiceName, String accessToken) throws Exception {

        String uri = "https://westus.tts.speech.microsoft.com/cognitiveservices/v1";
        URL url = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "application/ssml+xml");
        connection.setRequestProperty("X-Microsoft-OutputFormat", format);
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("User-Agent", "TTSAndroid");
        // 以下参数非必须
        connection.setRequestProperty("X-Search-ClientID", "");
        connection.setRequestProperty("X-Search-AppId", "");
        connection.setRequestProperty("Accept", "*/*");

        String body = createDom(language, gender, voiceName, text);
        byte[] bytes = body.getBytes();
        connection.setRequestProperty("content-length", String.valueOf(bytes.length));
        connection.connect();

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int len;
        byte[] buf = new byte[4096];
        while ((len = inputStream.read(buf)) > 0) {
            baos.write(buf, 0, len);
        }

        inputStream.close();
        connection.disconnect();

        return baos.toByteArray();
    }

    private static String createDom(String language, String gender, String voiceName, String text) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            Element speak = doc.createElement("speak");
            speak.setAttribute("version", "1.0");
            speak.setAttribute("xml:lang", "en-us");
            Element voice = doc.createElement("voice");
            voice.setAttribute("xml:lang", language);
            voice.setAttribute("xml:gender", gender);
            voice.setAttribute("name", voiceName);
            voice.appendChild(doc.createTextNode(text));
            speak.appendChild(voice);
            doc.appendChild(speak);
        }
        return transformDom(doc);
    }

    private static String transformDom(Document doc) {
        StringWriter writer = new StringWriter();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return writer.getBuffer().toString().replaceAll("[\n\r]", "");
    }

}
