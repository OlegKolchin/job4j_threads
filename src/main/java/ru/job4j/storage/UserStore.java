package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return user != null && store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return user != null && store.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return user != null && store.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int told, int amount) {
        Optional<User> src = Optional.ofNullable(store.get(fromId));
        Optional<User> dest = Optional.ofNullable(store.get(told));
        if (src.isPresent() && dest.isPresent() && src.get().getAmount() >= amount) {
            dest.get().setAmount(dest.get().getAmount() + amount);
            src.get().setAmount(src.get().getAmount() - amount);
            return true;
        }
        return false;
    }
}
