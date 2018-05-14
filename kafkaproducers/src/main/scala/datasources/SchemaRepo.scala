package datasources

object SchemaRepo {
  lazy val GTPUColNames = Seq(
      /*  0 - 9*/ "sender_id", "session_id", "session_tag", "report_count", "report_time",
                  "report_utc_time", "user_plane_interface_type", "report_reason", "bearer_creation_time",
      
      /* 10- 19*/ "eps_bearer_id_nsapi", "linked_eps_bearer_id_nsapi", "imsi", "msisdn", "roaming_sub",
                  "charging_gw_add_name", "charging_id", "charging_characteristics", "imei", "rat_type",
      
      /* 20- 29*/ "visited_network", "geographic_loc_type", "lac", "rac", "cell_id", "sac", "tac",
                  "ecell_id", "requested_qos_qci_trafficclass", "requested_arp_preemption_vulnerability_delayclass",
      
      /* 30- 39*/ "requested_arp_priority_level_precedenceclass", "requested_arp_preemption_capability_reliabilityclass",
                  "requested_qos_maximum_bit_rate_uplink", "requested_qos_maximum_bit_rate_downlink", "requested_qos_guaranteed_bit_rate_uplink",
                  "requested_qos_guaranteed_bit_rate_downlink", "negotiated_qos_qci_trafficclass", "negotiated_arp_preemption_vulnerability_delayclass",
                  "negotiated_arp_priority_level_precedenceclass", "negotiated_arp_preemption_capability_reliabilityclass",
      
      /* 40- 49*/ "negotiated_qos_maximum_bit_rate_uplink", "negotiated_qos_maximum_bit_rate_downlink", "negotiated_qos_guaranteed_bit_rate_uplink",
                  "negotiated_qos_guaranteed_bit_rate_downlink", "apn_aggregated_maximum_bit_rate_downlink", "apn_aggregated_maximum_bit_rate_uplink",
                  "apn", "direct_tunnel_flag", "ue_ipv4_address", "ue_ipv6_address",
      
      /* 50- 59*/ "sgsn_gn_s4_rnc_iuu_s12_enb_s1u_gtp_u_ip_address", "ggsn_gn_sgw_s4_s12_iuu_s1u_gtp_u_ip_address", "s5_s8_sgw_gtp_u_ip_address",
                  "s5_s8_pgw_gtp_u_ip_address", "gn_s4_s11_ggsn_sgw_gtp_c_ip_address", "s5_s8_pgw_gtp_c_ip_address",
                  "overload", "user_plane_data_collection_start_time", "user_plane_data_collection_end_time", "initiator",
      
      /* 60- 69*/ "protocol", "protocol_attribute_1", "protocol_attribute_2", "application", "application_attribute_1",
                  "application_attribute_2", "application_attribute_3", "server_ip_address", "server_port_number", "delta_bytes_uplink",
      
      /* 70- 79*/ "delta_bytes_downlink", "delta_packets_uplink", "delta_packets_downlink", "delta_bytes_retransmits_uplink",
                  "delta_bytes_retransmits_downlink", "delta_packet_retransmits_uplink", "delta_packet_retransmits_downlink",
                  "delta_mean_throughput_uplink", "delta_mean_throughput_downlink", "delta_peak_throughput_uplink",
      
      /* 80- 89*/ "delta_peak_throughput_downlink", "delta_active_seconds_uplink", "delta_active_seconds_downlink", "delta_internet_delay",
                  "delta_radio_delay", "delta_application_response_time", "delta_flow_count", "traffic_type", "extension_information_indicator", "domain_name",
      
      /* 90- 99*/ "user_agent", "content_type", "interest_type", "campaign_url", "http_traffic_type", "home_page_detection",
                  "home_page", "page_download_time", "http_proxy_server_ip_address", "proxy_domain_name",
      
      /*100-109*/ "smart_tag_id", "smart_tag_url", "get_response_1xx", "get_response_2xx", "get_response_3xx",
                  "get_response_4xx", "get_response_5xx", "put_response_1xx", "put_response_2xx", "put_response_3xx",
      
      /*110-119*/ "put_response_4xx", "put_response_5xx", "get_first_sc_1xx", "get_first_sc_2xx", "get_first_sc_3xx",
                  "get_first_sc_4xx", "get_first_sc_5xx", "put_first_sc_1xx", "put_first_sc_2xx", "put_first_sc_3xx",
      
      /*120-129*/ "put_first_sc_4xx", "put_first_sc_5xx", "email_sender", "first_5_recipients", "number_of_recipients",
                  "type_of_call", "from_display_name", "from_address_uri", "to_display_name", "to_address_uri",
      
      /*130-139*/ "call_id", "uplink_rtp_avg_jitter", "downlink_rtp_avg_jitter", "packets_lost_uplink", "packets_lost_downlink",
                  "duration_of_the_call", "request_page", "video_container", "video_codec", "video_bitrate_kbps",
      
      /*140-149*/ "video_framerate", "resolution_width", "resolution_height", "content_duration", "sender_display_name",
                  "receiver_display_name", "out_going_msg_number", "in_coming_msg_number", "user_name", "log_in",
      
      /*150-   */ "log_out"
    )
    
  def gtpu = GTPUColNames
}