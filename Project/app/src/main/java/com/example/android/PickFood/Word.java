/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.PickFood;

public class Word {

    public String NameString;

    public String DescriptionString;

    public String urlString = "null";

    public String OwnerString;

    public String TypeString;

    public String LocationString;

    public int ImageResourceId = NO_IMAGE_PROVIDED;

    public static final int NO_IMAGE_PROVIDED = -1;

    public Word(String Name, String Description, String Url,
                String Owner, String Type, String Location) {
        NameString = Name;
        DescriptionString = Description;
        urlString = Url;
        OwnerString = Owner;
        TypeString = Type;
        LocationString = Location;
    }

    public String getDefaultTranslationId() {
        return NameString;
    }

    public String getDescriptionId() {
        return DescriptionString;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getOwnerString() {
        return OwnerString;
    }

    public String getTypeString() {
        return TypeString;
    }

    public String getLocationString() {
        return LocationString;
    }

    public boolean hasImage() {
        return urlString != "null";
    }

}