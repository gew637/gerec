/**
 * Copyright (C) 2016 VanillaSource
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.vanillasource.gerec.mediatype;

import com.vanillasource.gerec.resource.ResourceReference;
import com.vanillasource.gerec.http.HttpRequest;
import com.vanillasource.gerec.http.HttpResponse;
import com.vanillasource.gerec.GerecException;
import java.util.List;
import java.util.function.Function;
import java.net.URI;

/**
 * A collection of media-types that all produce the same java type.
 */
public final class MediaTypes<T> implements MediaType<T> {
   private List<MediaType<T>> mediaTypes;

   public MediaTypes(List<MediaType<T>> mediaTypes) {
      this.mediaTypes = mediaTypes;
   }

   @Override
   public void applyTo(HttpRequest request) {
      mediaTypes.forEach(mediaType -> mediaType.applyTo(request));
   }

   @Override
   public boolean isHandling(HttpResponse response) {
      return mediaTypes.stream().anyMatch(mediaType -> mediaType.isHandling(response));
   }

   @Override
   public T deserialize(HttpResponse response, Function<URI, ResourceReference> referenceProducer) {
      return mediaTypes.stream()
         .filter(mediaType -> mediaType.isHandling(response))
         .findFirst()
         .orElseThrow(() -> new GerecException("no matching media types found for "+response+", possible media types were: "+mediaTypes))
         .deserialize(response, referenceProducer);
   }

   @Override
   public void serialize(T object, HttpRequest request) {
      throw new UnsupportedOperationException("multiple media-types can not serialize object, you have to select a single media-type");
   }
}

