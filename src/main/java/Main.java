// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  /** Demonstrates using the Speech API to transcribe an audio file. */
  public static void main(String... args) throws Exception {
//      try {
//        File f = new File("./out.txt");
//        PrintWriter pw = new PrintWriter(new FileWriter(f,false));
//        String[] listFile = {"in1.flac", "in2.flac"};
//        for (String file : listFile) {
//          System.out.print(transcribe(file));
//          System.out.print(transcribe("in1.flac"));
//          pw.print(file);
//        }
//        pw.close();
//
//      } catch(IOException e) {
//        e.printStackTrace();
//      }
    String uri = "https://www.youtube.com/watch?v=AmclgO6w0C0";
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("cmd.exe", "/c", "cd C:\\\\" +
            " && mkdir youtube-tr" +
            " && cd C:\\youtube-tr" +
            " && del in.flac" +
            " && del in.m4a" +
            " && youtube-dl -o \"in.%(ext)s\" -x --audio-format m4a " +
            uri +
            " && ffmpeg -i in.m4a -ac 1 in.flac" +
            " && ffprobe -i in1.flac -show_entries -v -quiet -of csv=\"p=0\"");
    Process p = processBuilder.start();
    p.waitFor();
    transcribe("in.flac");

//    String[] fileList = {"in1.flac", "in2.flac"};
//    for (String str : fileList) {
//      transcribe(str);
//    }
  }
  public static String transcribe(String input) throws Exception {
    String out = "";
    try (SpeechClient speechClient = SpeechClient.create()) {

      // The path to the audio file to transcribe
//      String fileName = "./resources/" + input;
      String fileName = "C:/youtube-tr/" + input;

      // Reads the audio file into memory
      Path path = Paths.get(fileName);
      byte[] data = Files.readAllBytes(path);
      ByteString audioBytes = ByteString.copyFrom(data);

      // Builds the sync recognize request
      RecognitionConfig config =
              RecognitionConfig.newBuilder()
                      .setEncoding(AudioEncoding.FLAC)
//              .setSampleRateHertz(48000)
                      .setLanguageCode("en-US")
                      .build();
      RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

      // Performs speech recognition on the audio file
      RecognizeResponse response = speechClient.recognize(config, audio);
      List<SpeechRecognitionResult> results = response.getResultsList();

      for (SpeechRecognitionResult result : results) {
        // There can be several alternative transcripts for a given chunk of speech. Just use the
        // first (most likely) one here.
        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
        out = out.concat(String.format("Transcription: %s%n", alternative.getTranscript() + "\n"));
        System.out.printf("Transcription: %s%n", alternative.getTranscript());
      }
    }
    return out;
  }
}