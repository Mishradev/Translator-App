// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/monitored_resource.proto

package com.google.api;

public interface MonitoredResourceOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.MonitoredResource)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * The monitored resource type. This field must match the corresponding
   * [MonitoredResourceDescriptor.type][google.api.MonitoredResourceDescriptor.type] to this resource..  For example,
   * `"cloudsql_database"` represents Cloud SQL databases.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  java.lang.String getType();
  /**
   * <pre>
   * The monitored resource type. This field must match the corresponding
   * [MonitoredResourceDescriptor.type][google.api.MonitoredResourceDescriptor.type] to this resource..  For example,
   * `"cloudsql_database"` represents Cloud SQL databases.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <pre>
   * Values for some or all of the labels listed in the associated monitored
   * resource descriptor. For example, you specify a specific Cloud SQL database
   * by supplying values for both the `"database_id"` and `"zone"` labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  int getLabelsCount();
  /**
   * <pre>
   * Values for some or all of the labels listed in the associated monitored
   * resource descriptor. For example, you specify a specific Cloud SQL database
   * by supplying values for both the `"database_id"` and `"zone"` labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  boolean containsLabels(
      java.lang.String key);
  /**
   * Use {@link #getLabelsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getLabels();
  /**
   * <pre>
   * Values for some or all of the labels listed in the associated monitored
   * resource descriptor. For example, you specify a specific Cloud SQL database
   * by supplying values for both the `"database_id"` and `"zone"` labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getLabelsMap();
  /**
   * <pre>
   * Values for some or all of the labels listed in the associated monitored
   * resource descriptor. For example, you specify a specific Cloud SQL database
   * by supplying values for both the `"database_id"` and `"zone"` labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */

  java.lang.String getLabelsOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <pre>
   * Values for some or all of the labels listed in the associated monitored
   * resource descriptor. For example, you specify a specific Cloud SQL database
   * by supplying values for both the `"database_id"` and `"zone"` labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */

  java.lang.String getLabelsOrThrow(
      java.lang.String key);
}
