package com.example.demo.club.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -1335449086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClub club = new QClub("club");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath clubClsCd = createString("clubClsCd");

    public final ListPath<ClubInfo, QClubInfo> clubInfoList = this.<ClubInfo, QClubInfo>createList("clubInfoList", ClubInfo.class, QClubInfo.class, PathInits.DIRECT2);

    public final StringPath clubNm = createString("clubNm");

    public final NumberPath<Long> clubSeq = createNumber("clubSeq", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modDt = _super.modDt;

    //inherited
    public final StringPath regDt = _super.regDt;

    public final StringPath roomNm = createString("roomNm");

    public final StringPath schoolCd = createString("schoolCd");

    public QClub(String variable) {
        this(Club.class, forVariable(variable), INITS);
    }

    public QClub(Path<? extends Club> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClub(PathMetadata metadata, PathInits inits) {
        this(Club.class, metadata, inits);
    }

    public QClub(Class<? extends Club> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

