
/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author:
 *
 *************************************************************************/

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ArtCollage {

	// The orginal picture
	private Picture original;

	// The collage picture
	private Picture collage;

	// The collage Picture consists of collageDimension X collageDimension tiles
	private int collageDimension;

	// A tile consists of tileDimension X tileDimension pixels
	private int tileDimension;

	/*
	 * One-argument Constructor 1. set default values of collageDimension to 4 and
	 * tileDimension to 100 2. initializes original with the filename image 3.
	 * initializes collage as a Picture of tileDimension*collageDimension x
	 * tileDimension*collageDimension, where each pixel is black (see all
	 * constructors for the Picture class). 4. update collage to be a scaled version
	 * of original (see scaling filter on Week 9 slides)
	 *
	 * @param filename the image filename
	 */
	public ArtCollage(String filename) {

		this.collageDimension = 4;
		this.tileDimension = 100;
		this.original = new Picture(filename);
		this.collage = new Picture((collageDimension * tileDimension), (tileDimension * collageDimension));

		int w = tileDimension * collageDimension;

		int h = tileDimension * collageDimension;

		this.collage = scale(original, w, h);
	}

	/*
	 * Three-arguments Constructor 1. set default values of collageDimension to cd
	 * and tileDimension to td 2. initializes original with the filename image 3.
	 * initializes collage as a Picture of tileDimension*collageDimension x
	 * tileDimension*collageDimension, where each pixel is black (see all
	 * constructors for the Picture class). 4. update collage to be a scaled version
	 * of original (see scaling filter on Week 9 slides)
	 *
	 * @param filename the image filename
	 */
	public ArtCollage(String filename, int td, int cd) {

		this.collageDimension = cd;
		this.tileDimension = td;
		this.original = new Picture(filename);
		this.collage = new Picture((cd * td), (td * cd));

		int w = tileDimension * collageDimension;

		int h = tileDimension * collageDimension;

		this.collage = scale(original, w, h);

	}

	/*
	 * Returns the collageDimension instance variable
	 *
	 * @return collageDimension
	 */
	public int getCollageDimension() {
		return collageDimension;
	}

	/*
	 * Returns the tileDimension instance variable
	 *
	 * @return tileDimension
	 */
	public int getTileDimension() {

		return tileDimension;
	}

	/*
	 * Returns original instance variable
	 *
	 * @return original
	 */
	public Picture getOriginalPicture() {

		return original;
	}

	/*
	 * Returns collage instance variable
	 *
	 * @return collage
	 */
	public Picture getCollagePicture() {

		return collage;
	}

	/*
	 * Display the original image Assumes that original has been initialized
	 */
	public void showOriginalPicture() {
		original.show();
	}

	/*
	 * Display the collage image Assumes that collage has been initialized
	 */
	public void showCollagePicture() {
		collage.show();
	}

	private static Picture scale(Picture pic, int w, int h) {
		Picture p = new Picture(w, h);
		for (int tcol = 0; tcol < w; tcol++) {
			for (int trow = 0; trow < h; trow++) {
				int scol = tcol * pic.width() / w;

				int srow = trow * pic.height() / h;

				Color color = pic.get(scol, srow);

				p.set(tcol, trow, color);
			}
		}
		return p;
	}

	/*
	 * Replaces the tile at collageCol,collageRow with the image from filename Tile
	 * (0,0) is the upper leftmost tile
	 *
	 * @param filename image to replace tile
	 * 
	 * @param collageCol tile column
	 * 
	 * @param collageRow tile row
	 */
	
	//done
	public void replaceTile(String filename, int collageCol, int collageRow) {
		int w = tileDimension; // 100

		int h = tileDimension; // 100

		Picture rep = scale(new Picture(filename), w, h);

		for (int col = (collageCol * tileDimension); col < (collageCol * tileDimension) + tileDimension; col++)
			for (int row = (collageRow * tileDimension); row < (collageRow * tileDimension) + tileDimension; row++) {

				Color color = rep.get(col% w, row % h);
				collage.set(col, row, color);
			}
	}

	/*
	 * Makes a collage of tiles from original Picture original will have
	 * collageDimension x collageDimension tiles, each tile has tileDimension X
	 * tileDimension pixels
	 */
	
	//done
	public void makeCollage() {

		int w = tileDimension;

		int h = tileDimension;

		Picture scaled = scale(original, w, h);
		for (int row = 0; row < h * collageDimension; row += 1) {

			for (int col = 0; col < h * collageDimension; col += 1) {

				Color color = scaled.get(col % w, row % h);
				collage.set(col, row, color);
			}
		}
	}

	/*
	 * Colorizes the tile at (collageCol, collageRow) with component (see CS111 Week
	 * 9 slides, the code for color separation is at the book's website)
	 *
	 * @param component is either red, blue or green
	 * 
	 * @param collageCol tile column
	 * 
	 * @param collageRow tile row
	 */
	
	//done
	public void colorizeTile(String component, int collageCol, int collageRow) {

		for (int col = (collageCol * tileDimension); col < (collageCol * tileDimension) + tileDimension; col++)
			for (int row = (collageRow * tileDimension); row < (collageRow * tileDimension) + tileDimension; row++) {
				
				if (component.equals("red")) {
					Color color = collage.get(col, row);
					int r = color.getRed();
					collage.set(col, row, new Color(r, 0, 0));

				} 
				else if (component.equals("green")) {
					Color color2 = collage.get(col, row);
					int g = color2.getGreen();
					collage.set(col, row, new Color(0, g, 0));
			}
				else if (component.equals("blue")) {
					Color color1 = collage.get(col, row);
					int b = color1.getBlue();
					collage.set(col, row, new Color(0, 0, b));
				} 
				
			}
	}

	/*
	 * Grayscale tile at (collageCol, collageRow) (see CS111 Week 9 slides, the code
	 * for luminance is at the book's website)
	 *
	 * @param collageCol tile column
	 * 
	 * @param collageRow tile row
	 */
	
	//done
	public void grayscaleTile(int collageCol, int collageRow) {

				for (int col = (collageCol * tileDimension); col < (collageCol * tileDimension) + tileDimension; col++)
					for (int row = (collageRow * tileDimension); row < (collageRow * tileDimension) + tileDimension; row++) {

						Color color = collage.get(col, row);
						Color gray = Luminance.toGray(color);
						collage.set(col, row, gray);
					}
			}

	/*
	 *
	 * Test client: use the examples given on the assignment description to test
	 * your ArtCollage
	 */
	public static void main(String[] args) {

		ArtCollage art = new ArtCollage("PlocLilo.jpg",100,8);
		art.makeCollage();
		art.replaceTile("Flo.jpg",0,1);
		art.replaceTile("Flo.jpg",1,0);
		art.replaceTile("Flo.jpg",1,1);
		art.colorizeTile("green",0,0);
		art.colorizeTile("green", 4, 6);
		art.showCollagePicture();
	}
}
