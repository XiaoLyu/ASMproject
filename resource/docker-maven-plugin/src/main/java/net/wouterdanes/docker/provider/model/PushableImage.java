/*
    Copyright 2014 Lachlan Coote

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

*/

package net.wouterdanes.docker.provider.model;

import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Holds information about an image (or tag thereof) to be pushed at a later stage.
 */
public class PushableImage {

    private final String imageId;
    private final Optional<String> nameAndTag;

    public PushableImage(final String imageId, final Optional<String> nameAndTag) {
        notBlank(imageId, "Image id was null or empty");
        notNull(nameAndTag.orElse(""), "Name and tag was null or empty");

        this.imageId = imageId;
        this.nameAndTag = nameAndTag;
    }

    public String getImageId() {
        return imageId;
    }

    public Optional<String> getNameAndTag() {
        return nameAndTag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, nameAndTag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PushableImage)) {
            return false;
        }

        PushableImage other = (PushableImage) obj;
        return imageId.equals(other.getImageId()) && nameAndTag.equals(other.getNameAndTag());
    }

    @Override
    public String toString() {
        return "PushableImage["
                + "imageId=" + imageId
                + ", nameAndTag=" + nameAndTag.orElse("<Unspecified>")
                + "]";
    }

}
