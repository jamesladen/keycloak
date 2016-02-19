package org.keycloak.models.cache.infinispan.stream;

import org.keycloak.models.cache.entities.CachedGroup;
import org.keycloak.models.cache.entities.CachedRole;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class HasRolePredicate implements Predicate<Map.Entry<String, Object>>, Serializable {
    private String role;

    public static HasRolePredicate create() {
        return new HasRolePredicate();
    }

    public HasRolePredicate role(String role) {
        this.role = role;
        return this;
    }

    @Override
    public boolean test(Map.Entry<String, Object> entry) {
        Object value = entry.getValue();
        if (value == null) return false;
        if (value instanceof CachedRole) {
            CachedRole cachedRole = (CachedRole)value;
            if (cachedRole.getComposites().contains(role)) return true;
        }
        if (value instanceof CachedGroup) {
            CachedGroup cachedRole = (CachedGroup)value;
            if (cachedRole.getRoleMappings().contains(role)) return true;
        }
        return false;
    }
}
