namespace java com.nanxiaoqiang.test.thrift.demo2

/*
bool: 布尔值 (true or false), one byte
byte: 有符号字节
i16: 16位有符号整型
i32: 32位有符号整型
i64: 64位有符号整型
double: 64位浮点型
string: Encoding agnostic text or binary string
list<t1>: 元素类型为t1的有序表，容许元素重复。（有序表ordered list不知道如何理解？排序的？c++的vector不排序）
set<t1>:元素类型为t1的无序表，不容许元素重复。
map<t1,t2>: 键类型为t1，值类型为t2的kv对，键不容许重复。
*/

enum TweetType {
    TWEET,// 默认0
    RETWEET = 2,// 可以赋值
    DM = 0xa,// 可以用十六进制
    REPLY
}

struct Tweet{
    1:i32 id;
    2:string msg;
    3:i64 time;// thrift里没有时间Date，所以用i64代替(long)
    4:optional Location loc;
    5:optional string lauague = "chinese";
    6:optional TweetType tweetType = TweetType.TWEET;
}

struct Location{
    1:required double latitude;
    2:required double longitude;
}

service LongTimeMethod{
    void noResult(),// 程序执行完成之后等待返回
    oneway void showObj(1:Tweet tweet),// 只Request，不等待Response
    list<Tweet> getTimeLine(1:i64 time)// thrift里没有时间，所以用long(32位有符号整数代替)
}