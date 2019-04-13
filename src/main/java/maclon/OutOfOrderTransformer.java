package maclon;

import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Objects;


public class OutOfOrderTransformer implements ValueTransformerWithKey<String, String, String> {

    private KeyValueStore<String, Integer> stateStore;
    private final String storeName;

    public OutOfOrderTransformer(String storeName) {
        Objects.requireNonNull(storeName, "Store Name can't be null");
        this.storeName = storeName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        stateStore = (KeyValueStore) context.getStateStore(storeName);
    }

    @Override
    public String transform(String readOnlyKey, String value) {
        Integer result = Integer.valueOf(value);
        Integer stored = stateStore.get(readOnlyKey);
        if (stored != null) {
            if (result > stored) {
                stateStore.put(readOnlyKey, result);
            } else {
                result = stored;
            }

        } else {
            stateStore.put(readOnlyKey, result);
        }
        return result.toString();
    }

    @Override
    public void close() {
        //no-op
    }
}
