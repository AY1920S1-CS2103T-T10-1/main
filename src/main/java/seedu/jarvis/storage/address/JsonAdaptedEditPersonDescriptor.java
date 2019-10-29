package seedu.jarvis.storage.address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.address.EditAddressCommand.EditPersonDescriptor;
import seedu.jarvis.model.address.person.Address;
import seedu.jarvis.model.address.person.Email;
import seedu.jarvis.model.address.person.Name;
import seedu.jarvis.model.address.person.Phone;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link EditPersonDescriptor}
 */
public class JsonAdaptedEditPersonDescriptor implements JsonAdapter<EditPersonDescriptor> {
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEditPersonDescriptor} with the given description.
     *
     * @param name Name description, can be null.
     * @param phone Phone description, can be null.
     * @param email Email description, can be null.
     * @param address Address description, can be null.
     * @param tags {@code Set} of {@code JsonAdaptedTag}.
     */
    @JsonCreator
    public JsonAdaptedEditPersonDescriptor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                           @JsonProperty("email") String email, @JsonProperty("address") String address,
                                           @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code EditPersonDescriptor} into this class for Jackson use.
     *
     * @param editPersonDescriptor {@code EditPersonDescriptor} to be converted for Jackson use.
     */
    public JsonAdaptedEditPersonDescriptor(EditPersonDescriptor editPersonDescriptor) {
        name = editPersonDescriptor.getName().map(name -> name.fullName).orElse(null);
        phone = editPersonDescriptor.getPhone().map(phone -> phone.value).orElse(null);
        email = editPersonDescriptor.getEmail().map(email -> email.value).orElse(null);
        address = editPersonDescriptor.getAddress().map(address -> address.value).orElse(null);
        editPersonDescriptor.getTags().ifPresent(tags -> this.tags.addAll(
                tags.stream().map(JsonAdaptedTag::new).collect(Collectors.toList())));
    }

    /**
     * Converts this Jackson-friendly adapted descriptor into the model's {@code EditPersonDescriptor} object.
     *
     * @return {@code EditPersonDescriptor} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code EditPersonDescriptor}.
     */
    @Override
    public EditPersonDescriptor toModelType() throws IllegalValueException {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setName(new Name(name));
        editPersonDescriptor.setPhone(new Phone(phone));
        editPersonDescriptor.setEmail(new Email(email));
        editPersonDescriptor.setAddress(new Address(address));

        Set<Tag> setOfTags = new HashSet<>();
        if (!tags.isEmpty()) {
            for (JsonAdaptedTag jsonAdaptedTag : tags) {
                setOfTags.add(jsonAdaptedTag.toModelType());
            }
        }
        editPersonDescriptor.setTags(tags.isEmpty() ? null : setOfTags);

        return editPersonDescriptor;
    }
}
