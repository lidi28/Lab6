import java.io.*;

public class EncodingConverter {

    public void convert(String inFileName, String outFileName, String inEncoding, String outEncoding) {
        BufferedReader in;
        try {
            File f1 = new File("./" + inFileName);
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f1), inEncoding));


            FileOutputStream fos = new FileOutputStream(outFileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos, outEncoding);
            Writer out = new BufferedWriter(osw);

            int ch;
            while ((ch = in.read()) > -1) {
                out.write(ch);
            }

            out.close();

        } catch (UnsupportedEncodingException e) {
            System.out.println("Bad encoding");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error while reading");
            System.exit(1);
        }
    }
}
