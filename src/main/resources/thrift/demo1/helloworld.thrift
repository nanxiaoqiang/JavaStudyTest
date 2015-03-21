namespace java com.nanxiaoqiang.test.thrift.demo1
// 生成方式 bas:thrift -r -gen java helloworld.thrift
struct Helloworld{
    1:i32 id;
    2:string name;
}

service HelloworldService{
    bool insertHelloworld(1:Helloworld helloworld),
    bool removeHelloworld(1:i32 id),
    Helloworld getHelloworld(1:i32 id)
}