package com.example.demo.club.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubInfo is a Querydsl query type for ClubInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubInfo extends EntityPathBase<ClubInfo> {

    private static final long serialVersionUID = 1765847504L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubInfo clubInfo = new QClubInfo("clubInfo");

    public final QClub club;

    public final NumberPath<Long> clubInfoSeq = createNumber("clubInfoSeq", Long.class);

    public final DatePath<java.time.LocalDate> joinDate = createDate("joinDate", java.time.LocalDate.class);

    public final QMember member;

    public final StringPath position = createString("position");

    public final DatePath<java.time.LocalDate> withdrawDate = createDate("withdrawDate", java.time.LocalDate.class);

    public QClubInfo(String variable) {
        this(ClubInfo.class, forVariable(variable), INITS);
    }

    public QClubInfo(Path<? extends ClubInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubInfo(PathMetadata metadata, PathInits inits) {
        this(ClubInfo.class, metadata, inits);
    }

    public QClubInfo(Class<? extends ClubInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

