package anperez78.photosOrganiser.util;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Component
public class ImagesUtils {

    public String getMd5HashFromFile(final String pathName) throws IOException {

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(new File(pathName));
            return DigestUtils.md5DigestAsHex(fis).toUpperCase();
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
