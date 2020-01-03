package com.example.finalblogerapp;

public class BlogerDbSchema {

    public static final class PostTable{
        public static final String NAME="post";

        public static final class Cols
        {
            public static final String UUID="pId";
            public static final String TITLE="postTitle";
            public static final String DECS="postDesc";
            public static final int IMG = 0;
            public static final String USERID="user_id";
        }

    }
}
