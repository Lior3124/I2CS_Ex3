package assignments.Ex3;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	private int[][] _map;
	private boolean _cyclicFlag = true;
	
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w,h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
		/////// add your code below ///////
        _map = new int[w][h];
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                _map[i][j] = v;
            }
        }
		///////////////////////////////////
	}
	@Override
	public void init(int[][] arr) {
		/////// add your code below ///////
        if(arr == null || arr.length == 0){
            throw new RuntimeException("Array is null");
        }
        int check_ragged = arr[0].length;
        for(int i = 1; i < arr.length; i++){
            if(arr[i].length != check_ragged){
                throw new RuntimeException("Array is ragged");
            }
        }
        this._map = new int[arr.length][check_ragged];
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < check_ragged; j++){
                this._map[i][j] = arr[i][j];
            }
        }
		///////////////////////////////////
	}
	@Override
	public int[][] getMap() {
		int[][] ans = null;
		/////// add your code below ///////
        ans = new int[_map.length][_map[0].length];
        for(int i = 0; i < this._map.length; i++){
            for(int j = 0; j < this._map[i].length; j++){
                ans[i][j] = this._map[i][j];
            }
        }
		///////////////////////////////////
		return ans;
	}
	@Override
	/////// add your code below ///////
	public int getWidth() {
        int ans = -1;
        ans = _map[0].length;
        return ans;
    }
	@Override
	/////// add your code below ///////
	public int getHeight() {
        int ans = -1;
        ans = _map.length;
        return ans;
    }
	@Override
	/////// add your code below ///////
	public int getPixel(int x, int y) {
        int ans = -1;
        if(y<this._map[0].length && x<this._map.length){
            ans = this._map[x][y];
        }
        return ans;
    }
	@Override
	/////// add your code below ///////
	public int getPixel(Pixel2D p) {
        int ans = -1;
        int x = p.getX();
        int y = p.getY();
        if(y<this._map[0].length && x<this._map.length) {
            ans = _map[x][y];
        }
        return ans;
	}
	@Override
	/////// add your code below ///////
	public void setPixel(int x, int y, int v) {
        if(y<this._map[0].length && x<this._map.length) {
            _map[x][y] = v;
        }
    }
	@Override
	/////// add your code below ///////
	public void setPixel(Pixel2D p, int v) {
        int x = p.getX();
        int y = p.getY();
        if(y<this._map[0].length && x<this._map.length) {
            _map[x][y] = v;
        }
	}
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v) {
		int ans=0;
		/////// add your code below ///////
        int old_v = this.getPixel(xy);

        ans = floodFill(xy,new_v,old_v,_cyclicFlag);
		///////////////////////////////////
		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        Pixel2D[] ans = null;  // the result.

        //create a copy of the current map
        Map copy = new Map(this.getWidth(),this.getHeight(),0);
        for(int i=0;i<copy.getHeight();i++){
            for(int j=0;j<copy.getWidth();j++){
                copy.setPixel(j,i,this.getPixel(j,i));
            }
        }

        //fill all the colors except obs color in the matrix to be -1 (-1 because then we count, and we don't want to get confused later on) and fills obscolor to be -2(for the same reason as -1)
        for(int i =0; i<this.getHeight();i++){
            for(int j =0; j<this.getWidth();j++) {
                if (this.getPixel(j, i) != obsColor) {
                    this.setPixel(j, i, -1);
                }else{
                    this.setPixel(j,i,-2);
                }
            }
        }

        int count =this.new_fill(p1,p2,_cyclicFlag);
        if(count == -1 ){
            //copy the map from the copy made earlier
            for(int i=0;i<copy.getHeight();i++){
                for(int j=0;j<copy.getWidth();j++){
                    this.setPixel(j,i,copy.getPixel(j,i));
                }
            }
            return null;
        }
        ans = this.find_path(count,p2.getX(),p2.getY(),_cyclicFlag);
        ans[0] = p1;

        //copy the map from the copy made earlier
        for(int i=0;i<copy.getHeight();i++){
            for(int j=0;j<copy.getWidth();j++){
                this.setPixel(j,i,copy.getPixel(j,i));
            }
        }
        return ans;
    }
	@Override
	/////// add your code below ///////
	public boolean isInside(Pixel2D p) {

        boolean ans = true;
        int height = this._map[0].length;
        int width = this._map.length;
        if(p.getX()>=width || p.getY()>=height || p.getX()<0 || p.getY()<0){
            ans = false;
        }
        return ans;
	}

	@Override
	/////// add your code below ///////
	public boolean isCyclic() {
		return _cyclicFlag;
	}
	@Override
	/////// add your code below ///////
	public void setCyclic(boolean cy) {
        _cyclicFlag = cy;
    }
	@Override
	/////// add your code below ///////
    public Map2D allDistance(Pixel2D start, int obsColor) {
        Map2D ans = null;  // the result.
        Pixel2D[] pixel_arr = null;
        Index2D end = null;
        //create a copy of the current map
        ans = new Map(this.getWidth(),this.getHeight(),0);
        for(int i=0;i<ans.getHeight();i++){
            for(int j=0;j<ans.getWidth();j++){
                ans.setPixel(j,i,this.getPixel(j,i));
            }
        }

        for(int i=0;i<ans.getHeight();i++){
            for(int j=0;j<ans.getWidth();j++){
                end = new Index2D(j,i);
                pixel_arr = this.shortestPath(start,end,obsColor);
                if(pixel_arr == null){
                    ans.setPixel(j,i,-1);
                }else {
                    ans.setPixel(j, i, pixel_arr.length-1);
                }
            }
        }

        return ans;
    }


    ////////////////private_methods//////////////////
    public int floodFill(Pixel2D xy, int new_v, int old_v, boolean cyclic) {
        int ans =0;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{xy.getX(),xy.getY()});

        while(!q.isEmpty()){
            int [] curr = q.remove();
            int  x = curr[0];
            int y = curr[1];

            Index2D ind = new Index2D(x,y);
            if(this.isInside(ind) && this.getPixel(ind) == old_v) {
                this.setPixel(ind, new_v);
                ans++;

                Index2D right = new Index2D(x + 1, y);
                int[] right_arr = new int[]{x + 1, y};
                if (cyclic) {
                    right = new Index2D((x + 1) % this._map.length, y);
                    right_arr = new int[]{(x + 1) % this._map.length, y};
                }
                if (this.isInside(right) && this.getPixel(right) == old_v) {
                    q.add(right_arr);
                }

                Index2D left = new Index2D(x - 1, y);
                int[] left_arr = new int[]{x - 1, y};
                if (cyclic) {
                    left = new Index2D((x - 1+this._map.length) % this._map.length, y);
                    left_arr = new int[]{(x - 1+this._map.length) % this._map.length, y};
                }
                if (this.isInside(left) && this.getPixel(left) == old_v) {
                    q.add(left_arr);
                }

                Index2D up = new Index2D(x, y + 1);
                int[] up_arr = new int[]{x, y + 1};
                if (cyclic) {
                    up = new Index2D(x, (y + 1) % this._map[0].length);
                    up_arr = new int[]{x, (y + 1) % this._map[0].length};
                }
                if (this.isInside(up) && this.getPixel(up) == old_v) {
                    q.add(up_arr);
                }

                Index2D down = new Index2D(x, y - 1);
                int[] down_arr = new int[]{x, y - 1};
                if (cyclic) {
                    down = new Index2D(x, (y - 1+this._map[0].length) % this._map[0].length);
                    down_arr = new int[]{x, (y - 1+this._map[0].length) % this._map[0].length};
                }
                if (this.isInside(down) && this.getPixel(down) == old_v) {
                    q.add(down_arr);
                }
            }

        }

        return ans;
    }



    /**works the same as fill + flood fill, the only difference is that now we count each step in each path , by saving in the queue its coordinates with their count,
     * and it returns the number of steps it did until it got to p2
     * if it didn't make it to p2 return -1.
     */
    private int new_fill(Pixel2D p1,Pixel2D p2,boolean cyclic) {
        int ans = 1;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{p1.getX(), p1.getY(), 0});
        int count = 0;

        while (!q.isEmpty()) {
            int[] curr = q.remove();
            int x = curr[0];
            int y = curr[1];
            count = curr[2];
            Index2D newp = new Index2D(x, y);


                this.setPixel(x, y, count);
                count++;

                if(newp.equals(p2)){
                    return count-1;
                }
                Index2D up = new Index2D(x, y + 1);
                int[] up_arr = new int[]{x, y + 1,count};
                if (cyclic) {
                     up = new Index2D(x, (y + 1) % this._map.length);
                     up_arr = new int[]{x, (y + 1) % this._map.length,count};
                }
                if (this.isInside(up) && this.getPixel(up) == -1) {
                    q.add(up_arr);
                }

                Index2D right = new Index2D(x + 1, y);
                int[] right_arr = new int[]{x + 1, y,count};
                if (cyclic) {
                    right = new Index2D((x + 1) % this._map[0].length, y);
                    right_arr = new int[]{(x + 1) % this._map[0].length, y,count};
                }
                if (this.isInside(right) && this.getPixel(right) == -1) {
                    q.add(right_arr);
                }

                Index2D left = new Index2D(x - 1, y);
                int[] left_arr = new int[]{x - 1, y,count};
                if (cyclic) {
                    left = new Index2D((x - 1+this._map[0].length) % this._map[0].length, y);
                    left_arr = new int[]{(x - 1+this._map[0].length) % this._map[0].length, y,count};
                }
                if (this.isInside(left) && this.getPixel(left) == -1) {
                    q.add(left_arr);
                }

                Index2D down = new Index2D(x, y - 1);
                int[] down_arr = new int[]{x, y - 1,count};
                if (cyclic) {
                    down = new Index2D(x, (y - 1+this._map.length) % this._map.length);
                    down_arr = new int[]{x, (y - 1+this._map.length) % this._map.length,count};
                }
                if (this.isInside(down) && this.getPixel(down) == -1) {
                    q.add(down_arr);

                }

        }
        return count-1;
    }

    /**
     * finds the path of the map(from higher numbers to 0), gets the coordinates of the highest number and searches for a number -1 this numbers
     * when it finds it add its coordinated to an array of Index2D continues until reaches 0
     * if there is no path returns -1
     * @param count - the highest number
     * @param x - the x coordinate of the point with the highest number
     * @param y - the y coordinate of the point with the highest number
     * @param cyclic - is the matrix cyclic
     * @return an array of Index2D with the path to reach from 0 to count
     */
    private Index2D[] find_path(int count, int x, int y, boolean cyclic){
        Index2D[] arr =new Index2D[count+1];
        Index2D p = new Index2D(x,y);

        while(count !=0){
            //add the point we are currently looking at to the array
            arr[count] = p;
            count--;

            //get left right up down based on if cyclic
            Index2D right = new Index2D(p.getX() + 1, p.getY());
            Index2D left = new Index2D(p.getX() - 1, p.getY());
            Index2D up = new Index2D(p.getX(), p.getY() + 1);
            Index2D down = new Index2D(p.getX(), p.getY() - 1);
            if(cyclic){

                right = new Index2D((p.getX() + 1) %this._map[0].length, p.getY());
                left = new Index2D((p.getX() - 1 + this._map[0].length) %this._map[0].length, p.getY());
                up = new Index2D(p.getX(), (p.getY() + 1) % this._map.length);
                down = new Index2D(p.getX(), (p.getY() - 1 + this._map.length) % this._map.length);
            }
            //check if left right up down are the path if any of them are they are now p, the new point we look at.
            if(this.isInside(right) && this.getPixel(right)==count){
                p = right;
            }
            else if(this.isInside(left) && this.getPixel(left)==count){
                p = left;
            }
            else if(this.isInside(up) && this.getPixel(up)==count){
                p = up;
            }
            else if(this.isInside(down) && this.getPixel(down)==count){
                p = down;
            }
        }
        return arr;
    }

    /**
     * @param p a given map
     * @return true if p and this map have the same dimensions, otherwise false
     * finds this maps height and width and then compares it to p.getHeight and p.getWidth
     */
    public boolean sameDimensions(Map2D p) {
        boolean ans = false;
        int height = this._map.length;
        int width = this._map[0].length;
        if(p.getWidth() ==width && p.getHeight()== height){
            return true;
        }
        return ans;
    }

    /**
     * @param ob the reference object with which to compare.
     * @return true if the two maps are equals(have the same dimension and values) otherwise false
     * first uses sameDimensions to check both dimensions and then loops over all the indexes to check that all the values are the same
     */
    @Override
    public boolean equals(Object ob) {
        boolean ans = false;
        if(ob instanceof Map2D){
            if(!(this.sameDimensions((Map2D)ob))){
                return false;
            }
            for(int i = 0; i < this._map.length; i++){
                for(int j = 0; j < this._map[i].length; j++){
                    if(this.getPixel(i,j) != ((Map2D) ob).getPixel(i,j)){
                        return false;
                    }
                }
            }
            return true;
        }
        return ans;
    }

}
