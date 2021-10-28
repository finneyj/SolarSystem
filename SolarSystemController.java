/**
 * Defines the methods that a SolarSystemController is required to implement.
 * 
 * @see SolarSystemGUI
 * @see SolarSystem
 */
public interface SolarSystemController {
    /**
     * A default method that adds a new solar object with the given characterisitcs.
     * The object does not orbit around anything. (e.g. the sun) *
     * 
     * @param name   the name of the new solar object being created.
     * @param size   the size of the new object, in pixels.
     * @param colour the colour of the new object.
     */
    default void add(String name, double size, String colour) {
        add(name, 0, 0, size, 0, colour);
    }

    /**
     * Add a new solar object with the given characteristics, that orbits the centre
     * of the screen.
     * 
     * @param name            the name of the new solar object being created.
     * @param orbitalDistance the distance of the new object from the centre of the
     *                        screen.
     * @param initialAngle    the initial orbital angle of the object.
     * @param size            the size of the new object, in pixels.
     * @param speed           the rotational speed of the new object around the
     *                        centre of the screen.
     * @param colour          the colour of the new object.
     */
    public void add(String name, double orbitalDistance, double initialAngle, double size, double speed, String colour);

    /**
     * Add a new solar object with the given characteristics, that orbits a given
     * parent object (e.g. planet).
     * 
     * @param name            the name of the new solar object being created.
     * @param orbitalDistance the distance of the new object from its parent object.
     * @param initialAngle    the initial orbital angle of the object.
     * @param size            the size of the new object, in pixels.
     * @param speed           the rotational speed of the new object around its
     *                        parent object.
     * @param colour          the colour of the new object.
     * @param parentName      the name of the solar object that the new object
     *                        should orbit.
     */
    public void add(String name, double orbitalDistance, double initialAngle, double size, double speed, String colour,
            String parentName);

    /**
     * Removes the given object from the screen.
     * 
     * @param name the name of the object to remove
     */
    public void remove(String name);
}