function kafka_send(broker_list ,clitopic, key, msg)
        local cjson     = require "cjson"
        local client    = require "resty.kafka.client"
        local producer  = require "resty.kafka.producer"
 
         
        local cli = client:new(broker_list)
        local brokers, partitions = cli:fetch_metadata(clitopic)
        if not brokers then
                ngx.say("brokers, get faild. err: ", partitions)
        end

	local function my_partitioner(key, num, correlation_id)
	      math.randomseed(os.time())  
	      return math.random(num)-1
	end
 
        local bp = producer:new(broker_list, { producer_type = "async", partitioner = my_partitioner})
        local ok, err = bp:send(clitopic, key, msg)
 
        return ok, err
end


function serialize(t)  
    local mark={}  
    local assign={}  
  
    local function table2str(t, parent)  
        mark[t] = parent  
        local ret = {}  
  
        if table.isArray(t) then  
            for i,v in pairs(t) do  
                local k = tostring(i)  
                local dotkey = parent.."["..k.."]"  
                local t = type(v)  
                if t == "userdata" or t == "function" or t == "thread" or t == "proto" or t == "upval" then  
                    --ignore  
                elseif t == "table" then  
                    if mark[v] then  
                        table.insert(assign, dotkey.."="..mark[v])  
                    else  
                        table.insert(ret, table2str(v, dotkey))  
                    end  
                elseif t == "string" then  
                    table.insert(ret, string.format("%q", v))  
                elseif t == "number" then  
                    if v == math.huge then  
                        table.insert(ret, "math.huge")  
                    elseif v == -math.huge then  
                        table.insert(ret, "-math.huge")  
                    else  
                        table.insert(ret,  tostring(v))  
                    end  
                else  
                    table.insert(ret,  tostring(v))  
                end  
            end  
        else  
            for f,v in pairs(t) do  
                local k = type(f)=="number" and "["..f.."]" or f  
                local dotkey = parent..(type(f)=="number" and k or "."..k)  
                local t = type(v)  
                if t == "userdata" or t == "function" or t == "thread" or t == "proto" or t == "upval" then  
                    --ignore  
                elseif t == "table" then  
                    if mark[v] then  
                        table.insert(assign, dotkey.."="..mark[v])  
                    else  
                        table.insert(ret, string.format("%s=%s", k, table2str(v, dotkey)))  
                    end  
                elseif t == "string" then  
                    table.insert(ret, string.format("%s=%q", k, v))  
                elseif t == "number" then  
                    if v == math.huge then  
                        table.insert(ret, string.format("%s=%s", k, "math.huge"))  
                    elseif v == -math.huge then  
                        table.insert(ret, string.format("%s=%s", k, "-math.huge"))  
                    else  
                        table.insert(ret, string.format("%s=%s", k, tostring(v)))  
                    end  
                else  
                    table.insert(ret, string.format("%s=%s", k, tostring(v)))  
                end  
            end  
        end  
  
        return "{"..table.concat(ret,",").."}"  
    end  
  
    if type(t) == "table" then  
        return string.format("%s%s",  table2str(t,"_"), table.concat(assign," "))  
    else  
        return tostring(t)  
    end  
end  
-- local args = ngx.req.get_uri_args()
local broker_list = {
          { host = "127.0.0.1", port = 9092},
          { host = "127.0.0.1", port = 9093}
     }
local content = '{"user_id":"123131", "event_id" : "1231231" , "event_code":"test","channel_id":"app"}'
local ret, err = kafka_send(broker_list, "test", "UserAction", content)
ngx.say(err)
