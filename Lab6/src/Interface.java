import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import static java.lang.System.out;
import static java.lang.System.exit;

public class Interface {
    Interface() {
        ;
    }

    public void start(String args[]) {
        if (args.length != 4) {
            printBadUsage();
            exit(1);
        }

        String inFileName = args[0];
        String outFileName = args[1];
        String inEncoding = args[2];
        String outEncoding = args[3];

        try {
            if (!Charset.isSupported(inEncoding) || !Charset.isSupported(outEncoding)) {
                printBadEncoding();
                exit(1);
            }
        } catch (Exception e) {
            printBadEncoding();
            exit(1);
        }

        // https://storage.yandexcloud.net/kekdsvdfgfasdfew2erwe1/utf8.txt
        if (inFileName.startsWith("https://")) {
            workWithRemoteFile(inFileName, outFileName, inEncoding, outEncoding);
        } else {
            workWithLocalFile(inFileName, outFileName, inEncoding, outEncoding);
        }

    }

    private void workWithRemoteFile(String inFileName, String outFileName, String inEncoding, String outEncoding) {
        try {
            URL website = new URL(inFileName);
            InputStream inputStream = website.openStream();
            ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
            File file = new File("./download");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 1 << 24);
        } catch (Exception e) {
            printBadUrl();
            exit(1);
        }

        EncodingConverter ec = new EncodingConverter();

        ec.convert("download", outFileName, inEncoding, outEncoding);
    }

    private void workWithLocalFile(String inFileName, String outFileName, String inEncoding, String outEncoding) {
        EncodingConverter ec = new EncodingConverter();

        ec.convert(inFileName, outFileName, inEncoding, outEncoding);
    }

    private void printBadUsage() {
        out.println("Example of usage: java EncodingConverter in.txt out.txt utf8 cp1251");
    }

    private void printBadEncoding() {
        out.println("Bad encoding type");
    }

    private void printBadUrl() {
        out.println("Bad url");
    }
}
