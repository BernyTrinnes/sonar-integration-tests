package org.example.sit.rest;

import io.restassured.http.ContentType;

/**
 * The request specification types used by REST-assured.
 */
public enum RequestSpecificationType {
   JSON_UNSECURED(ContentType.JSON, true, false),
   JSON_SECURED(ContentType.JSON, true, true),
   UNSUPPORTED_TYPE_UNSECURED(ContentType.TEXT, false, false),
   UNSUPPORTED_TYPE_SECURED(ContentType.TEXT, false, true);
   
   private final ContentType contentType;
   private final boolean supportedContentType;
   private final boolean securedConnection;
   
   RequestSpecificationType(final ContentType pContentType, final boolean pIsSupportedContentType,
         final boolean pIsSecuredConnection) {
      this.contentType = pContentType;
      this.supportedContentType = pIsSupportedContentType;
      this.securedConnection = pIsSecuredConnection;
   }
   
   public ContentType getContentType() {
      return this.contentType;
   }
   
   public boolean isSupportedContentType() {
      return this.supportedContentType;
   }
   
   public boolean isSecuredConnection() {
      return this.securedConnection;
   }
}
