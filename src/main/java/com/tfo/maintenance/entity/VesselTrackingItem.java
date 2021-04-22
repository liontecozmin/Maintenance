package com.tfo.maintenance.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = ".dbo.VesselTrackingItem")
@Data
@Getter
public class VesselTrackingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    @CsvBindByName(column ="ID")
    private String id;
}
