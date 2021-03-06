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

package com.vanillasource.gerec.it;

import com.vanillasource.gerec.MediaType;
import com.vanillasource.gerec.AcceptMediaType;
import com.vanillasource.gerec.mediatype.jackson.JacksonMediaType;
import com.vanillasource.gerec.form.Form;
import com.vanillasource.gerec.form.FormAwareAcceptMediaType;

public class SearchPage {
   public static final AcceptMediaType<SearchPage> TYPE = new FormAwareAcceptMediaType<>(
      new JacksonMediaType<>(SearchPage.class, "application/vnd.test.searchpage"));
   private String greetingMessage;
   private Form searchForm;

   protected SearchPage() {
   }

   public Form getSearchForm() {
      return searchForm;
   }

   public String getGreetingMessage() {
      return greetingMessage;
   }
}

