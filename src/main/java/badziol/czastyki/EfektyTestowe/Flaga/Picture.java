package badziol.czastyki.EfektyTestowe.Flaga;

/**
 *  Rozstaw czastek co 0.2
 *  Code page 437 - jesli chodzi o pomysl filipa(znaki)
 */

import badziol.czastyki.Czastyki;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.util.ArrayList;

public class Picture {
    private final Czastyki plugin;
    public final int width = 64;
    public final int height = 32;
    public final int transparentColor = 1;
    private final double psXo = -2; // particle start X offset
    private final double psYo = 2; // particle start Y offset
    private final double psZo = 4;  // particle start Z offset
    private final double dpXo = 0.18; // distance between particle in X axis
    private final double dpYo = 0.18; // distance between particle in Y axis

    private final double dasXo = 0.25; //distance between armorstand name tag , witch is single char
    private final double dasYo = 0.25; //distance between armorstand name tag , witch is single char

    private int[][] picFrame = new int[height][width];
    private int[][] flag  = new int[50][24]; //Hotfix

    private final ArmorStandPixel[][]  aspArr = new ArmorStandPixel[height][width];

    //anim part
    private float t;

    //private ControlPoint[] cp = new ControlPoint[4];
    private final ArrayList<ControlPoint> cp = new ArrayList<>();
    // first point is static
    int firstPointStartYposition = 4;
    int firstPointStartXposition = 0;

    //second point to bound curve
    int secondPointStartYposition = 0;
    int secondPointStartXposition = 24;
    int secondPointYmin = -5;
    int  secondPointYmax = 10;
    int secondPointYdir = 1;
    float secondPointYcurrentPosition = secondPointStartYposition;

    //third point to bound curve
    int  thirdPointStartYposition = 0;
    int  thirdPointStartXposition = 41;
    int  thirdPointYmin = -5;
    int  thirdPointYmax = 20;
    int thirdPointYdir = 1;
    float thirdPointYCurrentPosition = thirdPointStartYposition;

    //fourth point , last point of curve , is directly located in space.
    //!!!! changed form FLAG_WIDTH -> width!
    int  fourthPointStartXposition = width - 1; //63
    int  fourthPointStartYposition = 3;
    int  fourthPointYrange = 3;
    int fourthPointCycle = 3;
    int fourthPointCycleCurrent = 0;
    int fourthPointYdir = 1;
    float fourthPointYCurrentPosition = fourthPointStartYposition;
    
    private final ControlPoint[] vector = new ControlPoint[width];

    public Picture(Czastyki plugin) {
        this.plugin = plugin;
        mapControlPoints();
        for (int i = 0; i < width; i++) {
            vector[i] = new ControlPoint(0,0);
        }
    }

    // --- PAINTING PART------------------------------------------------------------------------------------------------
    public void drawPixel(int x , int y, int color){
        if (y < 0 || y>= height) return;
        if (x <0 || x>=width) return;
        picFrame[y][x] = color;
    }

    public void drawLine(int x0,int y0, int x1, int y1, int color){

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx-dy;
        int e2;

        while (true)
        {
            drawPixel(x0,y0,color);
            //frame[y0][x0] = color;

            if (x0 == x1 && y0 == y1) break;

            e2 = 2 * err;
            if (e2 > -dy)
            {
                err = err - dy;
                x0 = x0 + sx;
            }

            if (e2 < dx)
            {
                err = err + dx;
                y0 = y0 + sy;
            }
        }
    }

    public void drawFastVLine(int x , int y,int height , int color){
        drawLine(x,y,x,y+height-1,color);
    }

    public void  drawFastHLine(int x , int y, int width, int color){
        drawLine(x,y,x+width-1,y,color);
    }

    private void fillCircleHelper(int x0 , int y0, int r, int corners, int delta, int color){
        int f = 1-r;
        int ddF_x = 1;
        int ddF_y = -2 *r;
        int x = 0;
        int y =r;
        int px = x;
        int py = y;
        delta++;
        while (x < y) {
            if (f >= 0) {
                y--;

                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x;

            if (x < (y + 1)) {
                //if (corners & 1)
                    drawFastVLine(x0 + x, y0 - y, 2 * y + delta, color);
               // if (corners & 2)
                    drawFastVLine(x0 - x, y0 - y, 2 * y + delta, color);
            }
            if (y != py) {
                //if (corners & 1)
                    drawFastVLine(x0 + py, y0 - px, 2 * px + delta, color);
                //if (corners & 2)
                    drawFastVLine(x0 - py, y0 - px, 2 * px + delta, color);
                py = y;
            }
            px = x;
        }
    }

    public void drawFillCircle(int x, int y, int r, int color){
        drawFastVLine(x,y-r,2*r+1,color);
        fillCircleHelper(x,y,r,3,0,color);
    }

    public void drawCircle( int centerX, int centerY,int radius, int color) {
        int y = radius;
        int x = 0;

        int delta = 3 - 2 * radius;
        while (y >= x) {
            //drawPixelAndReflect(centerX, centerY, x, y, color);
            drawLine(centerX + x, centerY + y, centerX + x, centerY + y,color);
            drawLine(centerX + x, centerY - y, centerX + x, centerY - y,color);
            drawLine(centerX - x, centerY + y, centerX - x, centerY + y,color);
            drawLine(centerX - x, centerY - y, centerX - x, centerY - y,color);

            drawLine(centerX - y, centerY + x, centerX - y, centerY + x,color);
            drawLine(centerX - y, centerY - x, centerX - y, centerY - x,color);
            drawLine(centerX + y, centerY + x, centerX + y, centerY + x,color);
            drawLine(centerX + y, centerY - x, centerX + y, centerY - x,color);

            if (delta < 0) {
                //delta = calculateDeltaForHorizontalPixel(delta, x);
                delta = delta + 4 * x + 6;
            } else {
                //delta = calculateDeltaForDiagonalPixel(delta, x, y);
                delta = delta + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    public void clearBuffer(){
        for (int y = 0; y < height ; y++) {
            for (int x = 0; x<width; x++){
              picFrame[y][x]=transparentColor;
            }

        }
    }

    // --- TEST PAINTING PART ------------------------------------------------------------------------------------------
    public void drawTextBuffer(){
        for (int y = 0; y < height ; y++) {
            String str="";
            for (int x = 0; x<width; x++){
                str+= picFrame[y][x];
            }
            System.out.println(str);
        }
    }

    public void testPictureConsole(){
        clearBuffer();
        drawCircle(8,8,7,1);
        drawFillCircle(24,8,7,2);
        drawFillCircle(50,8,7,3);
        drawLine(0,31,63,31,4);

    }

    public void testPictureGame(){
        clearBuffer();
        drawCircle(8,8,7,0xff0000);
        drawFillCircle(24,8,7,0x00ff00);
        drawFillCircle(50,8,7,0x0000ff);
        drawLine(0,31,63,31,0xffff00);
        drawPixel(0,0,0xff0000);
        drawPixel(width-1,0,0x00fff00);
        drawPixel(0,height-1,0x0000ff);
        drawPixel(width-1 , height-1,0xffff00);

    }

    // --- PICTURE FOR TESTS -------------------------------------------------------------------------------------------
    public void flagRussia(int xo, int yo){
        final int FLAG_HEIGHT = 24;
        final int FLAG_WIDTH = 50;
        //int xo =7;
        //int yo =4;
        //white
        for (int y = 0; y < FLAG_HEIGHT / 3; y++) drawFastHLine(xo,yo+y,FLAG_WIDTH,0xffffff);
        //blue
        for (int y = FLAG_HEIGHT / 3; y < 2 * (FLAG_HEIGHT / 3); y++) drawFastHLine(xo,yo+ y,FLAG_WIDTH,0x0000ff);
        //red
        for (int y = 2 * (FLAG_HEIGHT / 3); y < FLAG_HEIGHT; y++) drawFastHLine(xo,yo + y,FLAG_WIDTH,0xff0000);
    }

    public void beniz(){
        int xo =7;
        int yo =4;
        //black  part
        drawFillCircle(xo + 20, yo + 18, 5, 0x000000);
        drawFillCircle(xo + 28, yo + 18, 5, 0x000000);
        drawFillCircle(xo + 24, yo + 4, 4, 0x000000);
        for (int x = 0; x < 9; x++) drawFastVLine(xo+ 20 + x, yo+ 5, 14, 0x000000);

        //pink part
        //CRGB(215, 65, 140)  -> d7,41,8c -> 0xd7418c
        drawFillCircle(xo + 20, yo + 18, 4, 0xd7418c);
        drawFillCircle(xo + 28, yo + 18, 4, 0xd7418c);
        drawFillCircle(xo + 24, yo + 4, 3, 0xd7418c);
        for (int x = 0; x < 7; x++) drawFastVLine(xo+ 21 + x, yo+ 5, 14, 0xd7418c);

        //black part 2 - border
        picFrame[yo+1][xo+24] =0x000000;
        drawFastHLine(xo+ 21, yo+ 5, 8, 0x000000);
    }

    public void pictureRussia(){
        clearBuffer();
        flagRussia(7,4);
        beniz();
    }

    // --- RENDER PICTURE USING PARTICLES ------------------------------------------------------------------------------
    public  void renderParticles(Player player){
        new BukkitRunnable(){
            final Location loc = player.getLocation();
            double xp;
            double yp;
            double zp;
            int couter = 0;

            int iCol =0;
            int iR = 0;
            int iG = 0;
            int iB = 0;
            @Override
            public void run() {
                for (int y = 0; y <height ; y++) {
                    for (int x =0; x<width; x++){
                        xp = psXo + ((width -1 - x) * dpXo);
                        yp = psYo + ((height - 1- y) * dpYo);
                        zp = psZo;
                        loc.add(xp,yp,zp);
                        iCol = picFrame[y][x];
                        iR = Color.fromRGB(iCol).getRed();
                        iG = Color.fromRGB(iCol).getGreen();
                        iB = Color.fromRGB(iCol).getBlue();
                        new ParticleBuilder(ParticleEffect.REDSTONE, loc)
                                .setParticleData(new RegularColor(iR,iG,iB))
                                .display();
                        loc.subtract(xp,yp,zp);
                    }
                }
                couter++;
                if (couter == 60) this.cancel();
            }
        }.runTaskTimerAsynchronously(plugin,0,1);

    }

    // --- ANIM PICTURE USING PARTICLES --------------------------------------------------------------------------------
    public void mapControlPoints(){
        /*
        cp[0] = new ControlPoint(firstPointStartXposition,firstPointStartYposition);
        cp[1] = new ControlPoint(secondPointStartXposition,secondPointStartYposition);
        cp[2] = new ControlPoint(thirdPointStartXposition,thirdPointStartYposition);
        cp[3] = new ControlPoint(fourthPointStartXposition,fourthPointYCurrentPosition);        *
         */
        cp.add(new ControlPoint(firstPointStartXposition,firstPointStartYposition));
        cp.add(new ControlPoint(secondPointStartXposition,secondPointStartYposition));
        cp.add(new ControlPoint(thirdPointStartXposition,thirdPointStartYposition));
        cp.add(new ControlPoint(fourthPointStartXposition,fourthPointYCurrentPosition));

    }

    public float Mix(float A, float B, float T) {
        return A * (1.0f - T) + B * T;
    }

    public float BezQuadra(float A, float B, float C, float T) {
        float AB = Mix(A, B, T);
        float BC = Mix(B, C, T);
        return Mix(AB, BC, T);
    }

    public float BezCubic(float A, float B, float C, float D, float T) {
        float ABC = BezQuadra(A, B, C, T);
        float BCD = BezQuadra(B, C, D, T);
        return Mix(ABC, BCD, T);
    }

    public void updateVector() {
        //first bounding point
        secondPointYcurrentPosition += secondPointYdir;
        if (secondPointYcurrentPosition == secondPointYmax || secondPointYcurrentPosition == secondPointYmin)
            secondPointYdir = secondPointYdir * -1;
        //cp[1].y = secondPointYcurrentPosition;

        cp.get(1).y = secondPointYcurrentPosition;
        //second bounding point
        thirdPointYCurrentPosition += thirdPointYdir;
        if (thirdPointYCurrentPosition > thirdPointYmax || thirdPointYCurrentPosition < thirdPointYmin)
            thirdPointYdir = thirdPointYdir * -1;
        //cp[2].y = thirdPointYCurrentPosition;

        cp.get(2).y = thirdPointYCurrentPosition;
        //move Y of last point;
        fourthPointCycleCurrent++;
        if (fourthPointCycleCurrent == fourthPointCycle) {
            fourthPointYCurrentPosition += fourthPointYdir;
            if (fourthPointYCurrentPosition == (fourthPointStartYposition + fourthPointYrange) ||
                    fourthPointYCurrentPosition == (fourthPointStartYposition - fourthPointYrange))
                fourthPointYdir = fourthPointYdir * -1;
            //cp[3].y = fourthPointYCurrentPosition;

            cp.get(3).y = fourthPointYCurrentPosition;
            fourthPointCycleCurrent = 0;
        }

        for (int i = 0; i < width; i++) {
            t = ((float)i) / (float)(width - 1);
            // vector[i].x = BezCubic(cp[0].x, cp[1].x, cp[2].x, cp[3].x, t);
            // vector[i].y = BezCubic(cp[0].y, cp[1].y, cp[2].y, cp[3].y, t);
            vector[i].x = BezCubic(cp.get(0).x, cp.get(1).x, cp.get(2).x,cp.get(3).x, t);
            vector[i].y = BezCubic(cp.get(0).y, cp.get(1).y, cp.get(2).y, cp.get(3).y, t);
        }
    }

    public void updateFrame(Player player){

        //updateVector();
        //for (ControlPoint controlPoint : vector)  System.out.println(controlPoint);

        new BukkitRunnable(){
            final Location loc = player.getLocation();
            double xp;
            double yp;
            double zp;
            int couter = 0;

            int iCol =0;
            int iR = 0;
            int iG = 0;
            int iB = 0;

            @Override
            public void run() {
                updateVector();

                for (int y = 0; y <height ; y++) {
                    for (int x =0; x<width; x++){
                        iCol = picFrame[y][x];
                        if (iCol == transparentColor) continue;
                        xp = psXo + ((width -1 - x) * dpXo);
                        yp = psYo + ((height - 1- y+vector[x].y) * dpYo);
                        zp = psZo;
                        loc.add(xp,yp,zp);

                        iR = Color.fromRGB(iCol).getRed();
                        iG = Color.fromRGB(iCol).getGreen();
                        iB = Color.fromRGB(iCol).getBlue();
                        new ParticleBuilder(ParticleEffect.REDSTONE, loc)
                                .setParticleData(new RegularColor(iR,iG,iB))
                                .display();
                        loc.subtract(xp,yp,zp);
                    }
                }
                couter++;
                if (couter == 160) this.cancel();
            }
        }.runTaskTimerAsynchronously(plugin,0,1);
    }

    // --- COMMON FOR RENDER PICTURE AND ANIMATION ---------------------------------------------------------------------
    public void prepereArmorstands(){
        double xp;
        double yp;
        double zp;
        for (int y = 0; y <height ; y++) {
            for (int x = 0; x<width; x++){
                xp = 250 + (width - 1 - x) *dasXo;
                yp = 103 + (height -1 - y) * dasYo;
                zp = 97;

                //xp = 250 + x * dasXo;
                //yp = 105 + y * dasYo;
                //zp = 97;
                aspArr[y][x] = new ArmorStandPixel(xp,yp,zp);
            }
        }
    }

    // --- RENDER USING ARMORSTANDS ------------------------------------------------------------------------------------
    public void  renderArmorStands(){
        int iR,iG,iB;
        int iCol;
        for (int y = 0; y <height ; y++) {
            for (int x =0; x<width; x++){
                iCol = picFrame[y][x];
                iR = Color.fromRGB(iCol).getRed();
                iG = Color.fromRGB(iCol).getGreen();
                iB = Color.fromRGB(iCol).getBlue();
                aspArr[y][x].setColor(iR,iG,iB);
            }
        }
    }

    // --- ANIM USING ARMORSTANDS --------------------------------------------------------------------------------------
    public void animArmorStands(){
        new BukkitRunnable(){
            int iR,iG,iB;
            int iCol;
            int couter = 0;
            int  yw = 0;
            @Override
            public void run() {
                updateVector();

                for (int y = 0; y <height ; y++) {
                    for (int x =0; x<width; x++){
                        yw = y + (int)vector[x].y;
                        if (yw < 0 || yw >= height -1) continue;
                        iCol = picFrame[y][x];
                        iR = Color.fromRGB(iCol).getRed();
                        iG = Color.fromRGB(iCol).getGreen();
                        iB = Color.fromRGB(iCol).getBlue();
                        aspArr[yw][x].setColor(iR,iG,iB); //!!!!!
                    }
                }
                couter++;
                if (couter == 160) this.cancel();
            }
        }.runTaskTimerAsynchronously(plugin,0,1);
    }

}
