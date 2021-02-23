import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

public class CharsToImage {
    public static Mat getCmdImage(){
        Scanner s = new Scanner(System.in);
        int m=s.nextInt(),n=s.nextInt();
        Mat mat = Mat.zeros(m,n, CvType.CV_8UC1);
        s.nextLine();
        for(int i=0;i<m;i++) {
            String row = s.nextLine();
            for (int j = 0; j < n; j++) {
                if(row.charAt(j) == '*'){
                    mat.put(i,j,255);
                }
            }
        }
        return mat;
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = getCmdImage();
        Imgcodecs.imwrite("content/res.jpg",m);
    }
}
