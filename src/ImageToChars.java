import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageToChars {
    private static double getPixelDensity(Mat m, int r1, int c1, int r2, int c2){
        double res = 0;
        for(int i=r1;i<=Math.min(r2,m.rows()-1);i++){
            for(int j=c1;j<=Math.min(c2,m.cols()-1);j++){
                res += m.get(i,j)[0] / 256.0;
            }
        }
        return res / ((r2-r1) * (c2-c1));
    }

    private static char getCharFromDensity(double pixelDensity){
        if(pixelDensity < 0.1){
            return '@';
        }else if(pixelDensity < 0.2){
            return '$';
        }else if(pixelDensity < 0.3){
            return '%';
        }else if(pixelDensity < 0.5){
            return '*';
        }else if(pixelDensity < 0.7){
            return '|';
        }else if(pixelDensity < 0.8){
            return ';';
        }else if(pixelDensity < 0.9){
            return '.';
        }else{
            return ' ';
        }
    }


    public static char[][] imageToSymbolsMap(Mat img,int m,int n) throws Exception { // (m,n) is a size of new image
        Mat gImg = new Mat();
        Imgproc.cvtColor(img,gImg,Imgproc.COLOR_BGR2GRAY);
        char[][] charImg = new char[m][n];
        int p = img.rows(), q = img.cols();

        if(p < m){
            throw new Exception("You can't use m greater than number of rows in a matrix");
        }
        if(q < n){
            throw new Exception("You can't use n greater than number of columns in a matrix");
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                int r1 =(int) (((double)p*i) / m);
                int c1 =(int) (((double)q*j) / n);
                int r2 =(int) (((double)p*(i+1)) / m);
                int c2 =(int) (((double)q*(j+1)) / n);
                double pixelDensity = getPixelDensity(gImg,r1,c1,r2,c2);
                charImg[i][j] =getCharFromDensity(pixelDensity);
            }
        }
        return charImg;
    }

    public static void main(String[] args) throws Exception { //Image to chars example with an image of a hockey stick
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("content/hockey stick.jpg");
        char[][] map = ImageToChars.imageToSymbolsMap(img,100,100);
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
