package application;
import javafx.scene.shape.Rectangle;

public class RecCollider extends WorldObject {

public RecCollider(double worldXPos, double worldYPos, double width, double length) {
super(new Rectangle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(width, length), true);
System.out.println(((Rectangle)super.hB).getWidth());

//Seal of interior rectangles with circle colliders
}



}