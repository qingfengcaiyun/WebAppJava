CREATE TABLE "Sys_Location"
(
	"locationId" serial not null PRIMARY KEY,
	"cnName" character varying(255),
	"enName" character varying(255),
	"levelNo" character varying(255),
	"parentNo" character varying(255),
	"levelCnName" character varying(255),
	"levelEnName" character varying(255),
	"isLeaf" boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_Location_locationId_idx" ON "Sys_Location" USING btree ("locationId");

CREATE TABLE "Sys_CustomArea"
(
	"areaId" bigserial not null PRIMARY KEY,
	"cnName" character varying(255),
	"enName" character varying(255),
	"itemIndex" integer not null
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_CustomArea_areaId_idx" ON "Sys_CustomArea" USING btree ("areaId");

CREATE TABLE "Sys_AreaRelation"
(
	"arId" bigserial not null PRIMARY KEY,
	"areaId" integer not null REFERENCES "Sys_CustomArea" ("areaId"),
	"locationId" integer not null REFERENCES "Sys_Location" ("locationId"),
	"itemIndex" integer not null
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_AreaRelation_arId_idx" ON "Sys_AreaRelation" USING btree ("arId");

CREATE TABLE "Sys_Function"(
	"funcId" serial not null PRIMARY KEY,
	"funcName" character varying(255),
	"funcNo" character varying(255),
	"parentNo" character varying(255),
	"funcUrl" character varying(255),
	"isLeaf" boolean NOT NULL DEFAULT true,
	"isDeleted" boolean NOT NULL DEFAULT false
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_Function_funcId_idx" ON "Sys_Function" USING btree ("funcId");

CREATE TABLE "Sys_Role"(
	"roleId" serial not null PRIMARY KEY,
	"roleName" character varying(255),
	"itemIndex" integer NOT NULL DEFAULT 100
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_Role_roleId_idx" ON "Sys_Role" USING btree ("roleId");

create table "Sys_User"(
    "userId" bigserial not null PRIMARY KEY,
    "userName" character varying(255),
    "userPwd" character(128),
    "md5Pwd" character(32),
    "userType" character(1),
    "lastLogin" timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
    "locationId" integer not null REFERENCES "Sys_Location" ("locationId"),
    "isDeleted" boolean NOT NULL DEFAULT false,
    "isLocked" boolean NOT NULL DEFAULT false,
    "insertTime" timestamp NOT NULL DEFAULT LOCALTIMESTAMP,
	"updateTime" timestamp NOT NULL DEFAULT LOCALTIMESTAMP
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_User_userId_idx" ON "Sys_User" USING btree ("userId");

create table "Sys_RoleFunc"(
	"rfId" bigserial not null PRIMARY KEY,
	"roleId" integer not null REFERENCES "Sys_Role" ("roleId"),
	"funcId" integer not null REFERENCES "Sys_Function" ("funcId")
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_RoleFunc_rfId_idx" ON "Sys_RoleFunc" USING btree ("rfId");

create table "Sys_UserRole"(
	"urId" bigserial not null PRIMARY KEY,
	"userId" bigint not null REFERENCES "Sys_User" ("userId"),
	"roleId" integer not null REFERENCES "Sys_Role" ("roleId")
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_UserRole_urId_idx" ON "Sys_UserRole" USING btree ("urId");

create table "Sys_UserFunc"(
	"ufId" bigserial not null PRIMARY KEY,
	"userId" bigint not null REFERENCES "Sys_User" ("userId"),
	"funcId" integer not null REFERENCES "Sys_Function" ("funcId")
)
WITH (
  OIDS=FALSE
);
CREATE UNIQUE INDEX "Sys_UserFunc_ufId_idx" ON "Sys_UserFunc" USING btree ("ufId");



/*
	levelEnName: Planet,Continent,Country,PoliticalArea,CustomArea,Province,City,Region
	levelCnName: 洲，国，行政区域，自定义区域，省，市，区县
*/
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('地球', 'Earth', '001', '0', '星球', 'Planet', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('亚洲', 'Asia', '001001', '001', '洲', 'Continent', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('中国', 'China', '001001001', '001001', '国家或地区', 'Country', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('华东', 'EastChina', '001001001001', '001001001', '行政区域', 'PoliticalArea', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('浙江', 'Zhejiang', '001001001001001', '001001001001', '行政省', 'Province', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('宁波', 'Ningbo', '001001001001001001', '001001001001001', '行政市', 'City', false);

INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('海曙', 'Haishu', '001001001001001001001', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('江东', 'Jiangdong', '001001001001001001002', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('江北', 'Jiangbei', '001001001001001001003', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('鄞州', 'Yinzhou', '001001001001001001001', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('北仑', 'Beilun', '001001001001001001005', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('镇海', 'Zhenhai', '001001001001001001006', '001001001001001001', '行政区县', 'Region', true);
INSERT INTO "Sys_Location" ("cnName", "enName", "levelNo", "parentNo", "levelCnName", "levelEnName", "isLeaf") VALUES ('高新区', 'Gaoxinqu', '001001001001001001007', '001001001001001001', '行政区县', 'Region', true);

INSERT INTO "Sys_Role" ("roleName", "itemIndex") VALUES ('根管理员', 1);
INSERT INTO "Sys_Role" ("roleName", "itemIndex") VALUES ('区域管理员', 2);
INSERT INTO "Sys_Role" ("roleName", "itemIndex") VALUES ('系统管理员', 3);
INSERT INTO "Sys_Role" ("roleName", "itemIndex") VALUES ('网站编辑', 4);


INSERT INTO "Sys_User" ("userName", "userPwd", "md5Pwd", "userType","locationId", "isDeleted", "isLocked") VALUES ('root', '6bc369d8851f6fc1990017132e47019506e2bdc7b39caa71ad5fe7653aea6675863a138faf62c4656aa8f3ff5ba646de954673bcf8cb28cfdfa4fc61cc9343ea', '63a9f0ea7bb98050796b649e85481845', 'R', 1, false, false);
INSERT INTO "Sys_User" ("userName", "userPwd", "md5Pwd", "userType","locationId", "isDeleted", "isLocked") VALUES ('admin', 'b6e59f07f0be7383affd26d3a7f1c882f39122b9d11d372ba0390da14e72d1406e97bb53bc06e99530055fafd255577a19b9a91a4f7c18d2949b4b8f49e3f73e', '21232f297a57a5a743894a0e4a801fc3','A', 6, false, false);
INSERT INTO "Sys_User" ("userName", "userPwd", "md5Pwd", "userType","locationId", "isDeleted", "isLocked") VALUES ('nb@zxrrt.com', '0aee918b8129610fec02ea94d4c1a84d0dcd8c5fbe4e7c8f61d0542efaaf73e9b1285b64e493d198e3ee3342402fdb44798ea19bbbe83fe601343185e61ae5f5','e10adc3949ba59abbe56e057f20f883e', 'A', 6, false, false);
INSERT INTO "Sys_User" ("userName", "userPwd", "md5Pwd", "userType","locationId", "isDeleted", "isLocked") VALUES ('editor001@nb.zxrrt.com', '0aee918b8129610fec02ea94d4c1a84d0dcd8c5fbe4e7c8f61d0542efaaf73e9b1285b64e493d198e3ee3342402fdb44798ea19bbbe83fe601343185e61ae5f5', 'e10adc3949ba59abbe56e057f20f883e','A', 6, false, false);

INSERT INTO "Sys_UserRole"("userId", "roleId")VALUES (1, 1);
INSERT INTO "Sys_UserRole"("userId", "roleId")VALUES (2, 2);
INSERT INTO "Sys_UserRole"("userId", "roleId")VALUES (3, 3);
INSERT INTO "Sys_UserRole"("userId", "roleId")VALUES (4, 4);

INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('系统设置', '001', '0', '', false, false);

INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('用户列表', '001001', '001', 'sys/user/page', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('角色管理', '001002', '001', 'sys/role/list', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('功能列表', '001003', '001', 'sys/function/tree', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('用户角色分配', '001004', '001', 'sys/userRole/view', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('角色功能分配', '001005', '001', 'sys/roleFunc/view', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('用户功能分配', '001006', '001', 'sys/userFunc/view', true, false);
INSERT INTO "Sys_Function" ("funcName", "funcNo", "parentNo", "funcUrl", "isLeaf", "isDeleted") VALUES ('地域管理', '001007', '001', 'sys/location/tree', true, false);

INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 1);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 2);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 3);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 4);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 5);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 6);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 7);
INSERT INTO "Sys_RoleFunc"("roleId", "funcId") VALUES (1, 8);

INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 1);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 2);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 3);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 4);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 5);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 6);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 7);
INSERT INTO "Sys_UserFunc"("userId", "funcId") VALUES (1, 8);