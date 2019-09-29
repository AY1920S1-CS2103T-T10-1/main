package seedu.jarvis.commons.util.andor;

import seedu.jarvis.model.course.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an And-Or Tree node, with each node representing either:
 * 1. an AND    (conjunction)
 * 2. an OR     (disjunction)
 * 3. data      (course data)
 */
public abstract class AndOrNode {
    protected Course data;
    protected AndOrNode parent;
    protected List<AndOrNode> children;

    public abstract boolean hasFulfilledCondition(Collection<Course> collection);
    public abstract String toString();

    protected AndOrNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    protected AndOrNode(Course data, AndOrNode parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public Course getData() {
        return this.data;
    }

    public List<AndOrNode> getChildren() {
        return this.children;
    }

    void insert(AndOrNode node) {
        this.children.add(node);
    }

    public static AndOrNode createAndOrNode(AndOrNode parent, String... type) {
        String nodeType = type.length == 0 ? "" : type[0];
        AndOrOperation andOrNodeType = AndOrOperationMapper.resolveType(nodeType);
        switch (andOrNodeType) {
        case AND:
            return new AndNode(null, parent);
        case OR:
            return new OrNode(null, parent);
        default:
            return null;
        }
    }

    public static AndOrNode createLeafNode(Course data, AndOrNode parent) {
        return new LeafNode(data, parent);
    }

    /**
     * Returns the {@code String} representation of this {@code AndOrNode} object and its
     * sub-trees.
     *
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications
     *
     * @return a String containing the String representation of this {@code AndOrNode} object,
     * and its sub-trees
     */
    public String toTreeString() {
        StringBuilder buffer = new StringBuilder();
        asStringTreeForm(buffer, "", "");
        return buffer.toString();
    }

    /**
     * Helper method to get this node plus its sub-trees in String form.
     *
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications
     */
    private void asStringTreeForm(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix).append(toString()).append("\n");
        for (Iterator<AndOrNode> it = children.iterator(); it.hasNext();) {
            AndOrNode child = it.next();
            if (it.hasNext()) {
                child.asStringTreeForm(buffer, childrenPrefix
                        + "├── ", childrenPrefix + "│   ");
            } else {
                child.asStringTreeForm(buffer, childrenPrefix
                        + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
