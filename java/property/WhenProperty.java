/*
 * GRAKN.AI - THE KNOWLEDGE GRAPH
 * Copyright (C) 2018 Grakn Labs Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package graql.lang.property;

import graql.lang.Graql;
import graql.lang.pattern.Conjunction;
import graql.lang.pattern.Pattern;
import graql.lang.statement.StatementType;

import static java.util.stream.Collectors.joining;

/**
 * Represents the {@code when} property on a rule.
 * This property can be inserted and not queried.
 * The when side describes the left-hand of an implication, stating that when the when side of a rule is true
 * the then side must hold.
 */
public class WhenProperty extends VarProperty {

    private final Pattern pattern;

    public WhenProperty(Pattern pattern) {
        if (pattern == null) {
            throw new NullPointerException("Null pattern");
        }
        this.pattern = pattern;
    }

    public Pattern pattern() {
        return pattern;
    }

    @Override
    public String keyword() {
        return Graql.Token.Property.WHEN.toString();
    }

    @Override @SuppressWarnings("Duplicates")
    public String property() {
        StringBuilder when = new StringBuilder();

        when.append(Graql.Token.Char.CURLY_OPEN).append(Graql.Token.Char.SPACE);

        if (pattern instanceof Conjunction) {
            when.append(((Conjunction<?>) pattern).getPatterns().stream()
                                .map(Object::toString)
                                .collect(joining(Graql.Token.Char.SPACE.toString())));
        } else {
            when.append(pattern);
        }

        when.append(Graql.Token.Char.SPACE).append(Graql.Token.Char.CURLY_CLOSE);
        return when.toString();
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public Class statementClass() {
        return StatementType.class;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof WhenProperty) {
            WhenProperty that = (WhenProperty) o;
            return (this.pattern.equals(that.pattern()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.pattern.hashCode();
        return h;
    }
}
