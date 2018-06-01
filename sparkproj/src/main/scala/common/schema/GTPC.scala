package common.schema

import org.apache.spark.sql.types._
object GTPC {
  
  lazy val colNames = List(
      /*  0-  9*/ "sender_id", "session_id", "session_tag", "report_count", "report_time",
                  "report_utc_time", "control_plane_interface_type", "report_reason", "signalling_start_time",

      /* 10- 19*/ "signalling_end_time", "bearer_creation_time", "eps_bearer_id_nsapi", "linked_eps_bearer_id_nsapi",
                  "imsi", "msisdn", "roaming_sub", "charging_gw_add_name", "charging_id", "charging_characteristics",

      /* 20- 29*/ "imei", "rat_type", "signaling_incomplete", "gtp_3gpp_cause_code", "gtp_3gpp_cause_source",
                  "session_context_drop_cause_code", "initiator", "total_signaling_messages", "selected_plmn_id", "mme_sgsn_overload_counter",

      /* 30- 39*/ "sgw_overload_counter", "pgw_overload_counter", "visited_network", "geographic_loc_type", "lac",
                  "rac", "cell_id", "sac", "tac", "ecell_id",

      /* 40- 49*/ "selection_mode", "apn", "direct_tunnel_flag", "p_tmsi_guti", "tlli", "relocation_cause_ranap",
                  "relocation_cause_s1_ap", "relocation_cause_bssgp", "teardown_ind", "pdn_type",
  
      /* 50- 59*/ "ue_ipv4_address", "ue_ipv6_address", "gn_s4_s11_sgsn_mme_gtp_c_ip_address", "gn_s4_s11_ggsn_sgw_gtp_c_ip_address",
                  "s5_s8_sgw_gtp_c_ip_address", "sgsn_gn_s4_rnc_iuu_s12_enb_s1u_gtp_u_ip_address", "s5_s8_sgw_gtp_u_ip_address",
                  "s5_s8_pgw_gtp_c_ip_address", "s5_s8_pgw_gtp_u_ip_address", "ggsn_gn_sgw_s4_s12_iuu_s1u_gtp_u_ip_address",
  
      /* 60- 69*/ "requested_qos_qci_trafficclass", "requested_arp_preemption_vulnerability_delayclass", "requested_arp_priority_level_precedenceclass",
                  "requested_arp_preemption_capability_reliabilityclass", "requested_qos_maximum_bit_rate_uplink", "requested_qos_maximum_bit_rate_downlink",
                  "requested_qos_guaranteed_bit_rate_uplink", "requested_qos_guaranteed_bit_rate_downlink", "negotiated_qos_qci_trafficclass",
                  "negotiated_arp_preemption_vulnerability_delayclass",

      /* 70- 79*/ "negotiated_arp_priority_level_precedenceclass", "negotiated_arp_preemption_capability_reliabilityclass", "negotiated_qos_maximum_bit_rate_uplink",
                  "negotiated_qos_maximum_bit_rate_downlink", "negotiated_qos_guaranteed_bit_rate_uplink", "negotiated_qos_guaranteed_bit_rate_downlink",
                  "apn_aggregated_maximum_bit_rate_downlink", "apn_aggregated_maximum_bit_rate_uplink", "request_user_plane_fteid_interface_type",
                  "request_user_plane_fteid_teid",

      /* 80- 89*/ "request_user_plane_fteid_ip_address", "response_user_plane_fteid_interface_type", "response_user_plane_fteid_teid",
                  "response_user_plane_fteid_ip_address", "requested_primary_dns", "requested_secondary_dns", "negotiated_primary_dns",
                  "negotiated_secondary_dns", "user_plane_data_collection_start_time", "user_plane_data_collection_end_time",

      /* 90- 99*/ "first_packet", "total_bytes_uplink", "total_bytes_downlink", "total_packets_uplink", "total_packets_downlink",
                  "delta_bytes_uplink", "delta_bytes_downlink", "delta_packets_uplink", "delta_packets_downlink", "delta_mean_throughput_uplink",

      /*100-109*/ "delta_mean_throughput_downlink", "delta_peak_throughput_uplink", "delta_peak_throughput_downlink", "delta_active_seconds_uplink",
                  "delta_active_seconds_downlink", "traffic_type", "s3_s10_s16_sgsn_src_mme_src_gtp_c_ip_address", "s3_s10_s16_sgsn_dst_mme_dst_gtp_c_ip_address",
                  "acknowledge_cause", "target_rnc_id", "target_enodeb_id"      
  )
  
  def schema(corruptedCol: String) = StructType(
  		  (corruptedCol :: colNames.reverse).reverse.map(new StructField(_, StringType, true))
      )
}