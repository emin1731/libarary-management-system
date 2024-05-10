// UpdateEvent represents an event that indicates an update or change has occurred in a component or system, extending EventObject to provide contextual information about the event's source.
package utils;

import java.util.EventObject;

public class UpdateEvent extends EventObject {
    public UpdateEvent(Object source) {
        super(source);
    }
}
