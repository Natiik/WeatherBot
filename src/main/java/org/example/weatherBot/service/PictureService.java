package org.example.weatherBot.service;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.example.weatherBot.entities.user_entity_structure.Metrics;
import org.example.weatherBot.response.Response;
import org.example.weatherBot.utility.DateUtil;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

@Service
public class PictureService {
    private String template = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 500.5 361\"> <g id=\"cloud\">\n" +
            "        <rect x=\"0.5\" y=\"0.5\" width=\"499.5\" height=\"360\" fill=\"#fff\"/>        <path fill=\"#d8d6d6\" d=\"M55,39.5A8.51,8.51,0,0,0,46.5,48v.19a4.11,4.11,0,0,0,.17,1l0,.05a22.46,22.46,0,0,0,1.06,2.8c.18.4.35.77.53,1.13.53,1.06,1.1,2,1.88,3.33A59.08,59.08,0,0,0,54,62.36s.08.08,1,1.2c0,0,0-.05.08-.07.14-.18.35-.51.6-.83s.61-.81,1.36-1.89c.42-.61.72-1,1.13-1.66.78-1.17,1.55-2.32,2.42-3.86.45-.79.81-1.46,1.06-2l.38-.77a18.34,18.34,0,0,0,1.28-3.25A8.81,8.81,0,0,0,63.5,48,8.51,8.51,0,0,0,55,39.5Zm0,12.62A4.12,4.12,0,1,1,59.12,48,4.13,4.13,0,0,1,55,52.12Z\"/>        <text  font-size=\"28.59px\" transform=\"translate(71.37 59.16)\" font-family=\"Times New Roman\" letter-spacing=\"-0.03em\"> <tspan> %s</tspan></text>        <text  font-size=\"61.1px\" fill=\"black\" letter-spacing=\"0.01em\" transform=\"translate(93.37 319.8)\" font-family=\"Times New Roman\">              <tspan  x=\"21.87\" y=\"0\"> %d°%s</tspan> </text>        <text font-size=\"25.27px\" font-family=\"Times New Roman\" transform=\"matrix(1, -0.02, 0.02, 1, 369.06, 114.47)\">            <tspan class=\"cls-15\"> %s %s</tspan></text>        <text  font-size=\"25.27px\" font-family=\"Times New Roman\" transform=\"matrix(1, -0.02, 0.02, 1, 369.06, 181.47)\">            <tspan  x=\"13.9\" y=\"0\"> %d%%</tspan></text>        <text font-size=\"25.27px\" font-family=\"Times New Roman\" transform=\"matrix(1, -0.02, 0.02, 1, 372.06, 261.47)\">            <tspan class=\"cls-20\" x=\"15.34\" y=\"0\"> %s</tspan></text>        <text font-size=\"25.27px\" font-family=\"Times New Roman\" transform=\"matrix(1, -0.02, 0.02, 1, 372.06, 309.47)\"> %s</text><circle fill=\"#d8d6d6\" cx=\"340.89\" cy=\"93.44\" r=\"2.58\"/>        <path fill=\"#d8d6d6\" d=\"M341.63,89.32a.39.39,0,0,1-.38.47h-.11a3.41,3.41,0,0,0-.72,0,3.53,3.53,0,0,0-1.61.45.79.79,0,0,1-.73,0l0,0a.86.86,0,0,1-.45-.76l0-5.21v-.34l-.07-9.8h0v-.53a.42.42,0,0,1,.41-.42h0a.52.52,0,0,1,.51.44l.06.26,2,9.71,0,.2S341.38,88.12,341.63,89.32Z\"/>        <path fill=\"#d8d6d6\" d=\"M343.69,96.14a.4.4,0,0,1-.24-.56l.06-.09a3,3,0,0,0,.38-.61,3.57,3.57,0,0,0,.37-1.64.76.76,0,0,1,.33-.64l0,0a.86.86,0,0,1,.89,0L350.12,95l.3.16,8.67,4.58h0l.47.25a.41.41,0,0,1,.18.55h0a.53.53,0,0,1-.63.25l-.26-.08-9.49-2.85-.2-.06Z\"/>        <path fill=\"#d8d6d6\" d=\"M336.33,94a.38.38,0,0,1,.59.11l.05.1a3.4,3.4,0,0,0,.29.65A3.55,3.55,0,0,0,338.41,96a.76.76,0,0,1,.36.64v0a.88.88,0,0,1-.46.76l-4.59,2.47-.3.16-8.63,4.66h0l-.47.25a.42.42,0,0,1-.56-.15h0a.55.55,0,0,1,.15-.67l.21-.17,7.65-6.3.16-.12S335.38,94.73,336.33,94Z\"/>        <rect fill=\"#d8d6d6\" x=\"340.37\" y=\"97.35\" width=\"1.91\" height=\"22.15\" transform=\"translate(-3.6 12.06) rotate(-2.01)\"/>        <path fill=\"#d8d6d6\" d=\"M341.73,185.92a11.44,11.44,0,0,0,11.46-11.44v-.26a5.19,5.19,0,0,0-.23-1.4.19.19,0,0,0,0-.07,29.06,29.06,0,0,0-1.43-3.77c-.24-.54-.47-1-.71-1.52-.72-1.43-1.48-2.72-2.54-4.49a74.23,74.23,0,0,0-5.19-7.84s-.1-.11-1.32-1.63c-.06,0,0,.07-.1.11-.2.23-.48.68-.81,1.12s-.83,1.08-1.84,2.54c-.56.81-1,1.39-1.53,2.24-1,1.56-2.07,3.12-3.26,5.19-.6,1.06-1.08,2-1.42,2.63l-.51,1a23.9,23.9,0,0,0-1.73,4.37,12.51,12.51,0,0,0-.26,1.73A11.45,11.45,0,0,0,341.73,185.92Z\"/>        <path fill=\"#d8d6d6\" d=\"M349.74,301.61a7.59,7.59,0,0,1-.1,1.27H348.5a7.23,7.23,0,0,0,.1-1.15,6.73,6.73,0,0,0-13.45-.05,6.12,6.12,0,0,0,.1,1.14h-1.4a8.93,8.93,0,0,1-.09-1.27,8,8,0,1,1,16,.06Z\"/>        <path fill=\"#d8d6d6\" d=\"M335,294.92a.61.61,0,0,1-.45.19.67.67,0,0,1-.45-.19l-2.32-2.34a.63.63,0,0,1,0-.9.66.66,0,0,1,.45-.18.61.61,0,0,1,.45.19L335,294A.62.62,0,0,1,335,294.92Z\"/>        <path fill=\"#d8d6d6\" d=\"M331.85,301.92a.63.63,0,0,1-.18.45.67.67,0,0,1-.45.18h-3.3a.64.64,0,1,1,0-1.27h3.29A.64.64,0,0,1,331.85,301.92Z\"/>        <path fill=\"#d8d6d6\" d=\"M356.21,302a.63.63,0,0,1-.64.63h-3.3a.64.64,0,0,1-.63-.64.66.66,0,0,1,.19-.45.63.63,0,0,1,.45-.18h3.3A.64.64,0,0,1,356.21,302Z\"/>        <path fill=\"#d8d6d6\" d=\"M355.94,306.58a.65.65,0,0,1-.64.64l-8.85,0-2.16,2h0l-1.88,1.73-.94.85-.64-.65-2.1-2.11h0l-1.82-1.84-9,0a.64.64,0,0,1-.63-.64.6.6,0,0,1,.19-.45.63.63,0,0,1,.45-.18l9.52,0,2.12,2.14h0l1.51,1.52h0l.44.44.45-.41,1.44-1.31,1.18-1.09,1.38-1.26,9.35,0A.63.63,0,0,1,355.94,306.58Z\"/>        <path fill=\"#d8d6d6\" d=\"M352.27,292.28a.6.6,0,0,1-.21.46l-2.46,2.2a.64.64,0,0,1-1.06-.48.73.73,0,0,1,.21-.47l2.47-2.19a.63.63,0,0,1,.89.05A.62.62,0,0,1,352.27,292.28Z\"/>        <path fill=\"#d8d6d6\" d=\"M342.44,288.14v3.29a.65.65,0,0,1-.64.64.64.64,0,0,1-.45-.19.67.67,0,0,1-.18-.45v-3.3a.64.64,0,0,1,.64-.63.6.6,0,0,1,.44.19A.6.6,0,0,1,342.44,288.14Z\"/>        <path fill=\"#d8d6d6\" d=\"M350.07,259a8.2,8.2,0,0,1-.1,1.32l-1.19,0a6.45,6.45,0,0,0,.1-1.17,7,7,0,0,0-14,0,7.45,7.45,0,0,0,.07.95l-1.45,0a7.35,7.35,0,0,1-.07-1.06,8.33,8.33,0,1,1,16.66,0Z\"/>        <path fill=\"#d8d6d6\" d=\"M334.69,252a.62.62,0,0,1-.46.19.66.66,0,0,1-.47-.19l-2.43-2.43a.66.66,0,0,1,.47-1.13.63.63,0,0,1,.46.19l2.43,2.43A.66.66,0,0,1,334.69,252Z\"/>        <path fill=\"#d8d6d6\" d=\"M331.43,259.35a.63.63,0,0,1-.2.46.66.66,0,0,1-.47.2h-3.43a.66.66,0,0,1-.66-.66.65.65,0,0,1,.66-.66h3.43A.66.66,0,0,1,331.43,259.35Z\"/>        <path fill=\"#d8d6d6\" d=\"M356.81,259.35a.63.63,0,0,1-.19.46.66.66,0,0,1-.47.2h-3.44a.67.67,0,0,1-.66-.66.66.66,0,0,1,.2-.47.64.64,0,0,1,.46-.19h3.44A.65.65,0,0,1,356.81,259.35Z\"/>        <path fill=\"#d8d6d6\" d=\"M356.55,264.11a.67.67,0,0,1-.19.47.71.71,0,0,1-.47.19h-9.66l-1.33-1.32-3.1-3.11-3.36,3.11L337,264.77h-9.67a.66.66,0,0,1-.66-.66.65.65,0,0,1,.66-.66h9.16l3.61-3.33,1.75-1.6,1.65,1.65,3.28,3.28h9.11A.66.66,0,0,1,356.55,264.11Z\"/>        <path fill=\"#d8d6d6\" d=\"M352.67,249.21a.64.64,0,0,1-.22.49L349.9,252a.66.66,0,0,1-.94,0,.7.7,0,0,1-.17-.45A.68.68,0,0,1,349,251l2.56-2.29a.65.65,0,0,1,.93,0A.7.7,0,0,1,352.67,249.21Z\"/>        <path fill=\"#d8d6d6\" d=\"M342.4,244.93v3.44a.66.66,0,1,1-1.32,0v-3.44a.66.66,0,0,1,.66-.66.67.67,0,0,1,.47.2A.63.63,0,0,1,342.4,244.93Z\"/> %s </g> </svg>\n";


    public void createSVG(Long id, Response response, Metrics metrics) {
        try (PrintWriter writer = new PrintWriter("%s.svg".formatted(id.toString()))) {
            writer.write(String.format(
                    template,
                    response.getName(),
                    (int) Math.round(response.getMain().getTemp()),
                    metrics.getTemperature(),
                    response.getWind().getSpeed(),
                    metrics.getSpeed(),
                    response.getMain().getHumidity(),
                    DateUtil.toNormalTime(response.getSys().getSunrise()),
                    DateUtil.toNormalTime(response.getSys().getSunset()),
                    "<path fill=\"#d8d6d6\" d=\"M247.91,160.78a46.07,46.07,0,0,1-2.09,13.87,46.75,46.75,0,0,1-15.89,23.06,18.74,18.74,0,0,1-11.59,3.79H105.91a33.41,33.41,0,0,1-.08-66.82c.37-1.42.83-2.8,1.33-4.18a54.32,54.32,0,0,1,51-35.5c16.41,0,29.23,8.35,41.09,18.84.59,0,1.13,0,1.72,0A46.7,46.7,0,0,1,236.8,130.5a46.64,46.64,0,0,1,11.11,30.28Z\"/> <circle fill=\"#d8d6d6\" cx=\"178.5\" cy=\"220.5\" r=\"8\"/> <circle fill=\"#d8d6d6\" cx=\"137.5\" cy=\"220.5\" r=\"8\"/>"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SVGtoJPG(Long id) {
        JPEGTranscoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);
        t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, 499.5f);
        t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, 360f);
        try {
            TranscoderInput input = new TranscoderInput(new FileInputStream("%s.svg".formatted(id.toString())));
            TranscoderOutput output = new TranscoderOutput(new FileOutputStream("%s.jpg".formatted(id.toString())));
            t.transcode(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
