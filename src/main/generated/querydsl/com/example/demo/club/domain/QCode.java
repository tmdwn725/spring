package com.example.demo.club.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCode is a Querydsl query type for Code
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCode extends EntityPathBase<Code> {

    private static final long serialVersionUID = -1335446727L;

    public static final QCode code = new QCode("code");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> cd = createNumber("cd", Long.class);

    public final StringPath codeNm = createString("codeNm");

    //inherited
    public final StringPath modDt = _super.modDt;

    //inherited
    public final StringPath regDt = _super.regDt;

    public QCode(String variable) {
        super(Code.class, forVariable(variable));
    }

    public QCode(Path<? extends Code> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCode(PathMetadata metadata) {
        super(Code.class, metadata);
    }

}

