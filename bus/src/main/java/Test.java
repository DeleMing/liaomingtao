/**
 * @author: LiaoMingtao
 * @date: 2022/7/15
 */
public class Test {

    public static void main(String[] args) {
        String filterStr = "filter {\n" +
                "\tif[component_name] and [component_name] == 'memcached'{\n" +
                "\t  ruby {\n" +
                "\t\tinit => \"\n" +
                "\t\t  def coalesce(event, default, *sources)\n" +
                "\t\t\tsource = sources.find {|x| event.get(x)}\n" +
                "\t\t\treturn source ? event.get(source) : default\n" +
                "\t\t  end\n" +
                "\t\t\"\n" +
                "\t\tcode => \"\n" +
                "\t\t  event.set('offset', coalesce(event, 0, '[offset]', '[log][offset]'))\n" +
                "\t\t  event.set('source', coalesce(event, '', '[source]', '[log][file][path]'))\n" +
                "\t\t  event.set('timestamp', event.get('@timestamp'))\n" +
                "\t\t  event.set('dimensions', {\n" +
                "\t\t\t  'hostname' => coalesce(event, 'emptyHost', '[beat][hostname]', '[host][name]'),\n" +
                "\t\t\t  'appsystem' =>  event.get('appsystem'),\n" +
                "\t\t\t  'appprogramname' => event.get('appprogramname'),\n" +
                "\t\t\t  'servicename' => event.get('servicename'),\n" +
                "\t\t\t  'servicecode' => event.get('servicecode'),\n" +
                "\t\t\t  'clustername' => event.get('clustername'),\n" +
                "\t\t\t  'ip' => event.get('ip')\n" +
                "\t\t  })\n" +
                "\t\t  event.set('normalFields', {\n" +
                "\t\t\t  'collecttime' => event.get('@timestamp').to_s,\n" +
                "\t\t\t  'message' => event.get('message')\n" +
                "\t\t  })\n" +
                "\t\t  event.set('normalFields.message', event.get('message'))\n" +
                "\n" +
                "\t\t  event.remove('log')\n" +
                "\t\t  event.remove('@timestamp')\n" +
                "\t\t  event.remove('beat')\n" +
                "\t\t  event.remove('host')\n" +
                "\t\t  event.remove('appsystem')\n" +
                "\t\t  event.remove('appprogramname')\n" +
                "\t\t  event.remove('servicename')\n" +
                "\t\t  event.remove('servicecode')\n" +
                "\t\t  event.remove('clustername')\n" +
                "\t\t  event.remove('ip')\n" +
                "\t\t  event.remove('message')\n" +
                "\t\t\"\n" +
                "\t  }\n" +
                "\t  \n" +
                "\t  ruby {\n" +
                "\t\tcode => \"\n" +
                "\t\t  event.set('grokparsefailure', true)\n" +
                "\t\t\"\n" +
                "\t  }\n" +
                "\t  if [grokparsefailure] == 'true' {\n" +
                "\t\tmutate {\n" +
                "\t\t  remove_field => [\n" +
                "\t\t\t\"grokparsefailure\"\n" +
                "\t\t  ]\n" +
                "\t\t}\n" +
                "\t\tgrok {\n" +
                "\t\t  match => {\n" +
                "\t\t\t\"normalFields.message\" => \"^(?<normalFields.direction><|>)?%{NUMBER:normalFields.socket_descriptor}.*$\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t\truby {\n" +
                "\t\t  code => \"\n" +
                "\t\t\tevent.set('grokparsefailure', coalesce(event, 0,'normalFields.socket_descriptor'))\n" +
                "\t\t  \"\n" +
                "\t\t}\n" +
                "\t\tif !([grokparsefailure] == 0) {\n" +
                "\t\t  mutate {\n" +
                "\t\t\tadd_field => {\n" +
                "\t\t\t  \"logTypeName\" => \"memcached_log_format1\"\n" +
                "\t\t\t}\n" +
                "\t\t\tremove_field => [\n" +
                "\t\t\t  \"grokparsefailure\"\n" +
                "\t\t\t]\n" +
                "\t\t  }\n" +
                "\t\t  ruby {\n" +
                "\t\t\tcode => \"\n" +
                "\t\t\t  event.set('[normalFields][socket_descriptor]', event.get('normalFields.socket_descriptor').to_f)\n" +
                "\t\t\t  event.remove('normalFields.socket_descriptor')\n" +
                "\t\t\t\n" +
                "\t\t\t  event.set('normalFields.direction', coalesce(event, 0, 'normalFields.direction'))\n" +
                "\t\t\t  if event.get('normalFields.direction') == 0\n" +
                "\t\t\t\tevent.remove('normalFields.direction')\n" +
                "\t\t\t   elsif(\n" +
                "\t\t\t\tevent.set('[normalFields][direction]',event.get('normalFields.direction'))\n" +
                "\t\t\t\tevent.remove('normalFields.direction')\n" +
                "\t\t\t\t)\n" +
                "\t\t\t  end\n" +
                "\t\t\t\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t  }\n" +
                "\n" +
                "\t  if [grokparsefailure] == 0 {\n" +
                "\t\tmutate {\n" +
                "\t\t  remove_field => [\n" +
                "\t\t\t\"grokparsefailure\"\n" +
                "\t\t  ]\n" +
                "\t\t}\n" +
                "\t\tgrok {\n" +
                "\t\t  match => {\n" +
                "\t\t\t\"normalFields.message\" => \"^%{NUMBER:normalFields.socket_descriptor}:.*$\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t\truby {\n" +
                "\t\t  code => \"\n" +
                "\t\t\tevent.set('grokparsefailure', coalesce(event, 0,'normalFields.socket_descriptor'))\n" +
                "\t\t  \"\n" +
                "\t\t}\n" +
                "\t\tif !([grokparsefailure] == 0) {\n" +
                "\t\t  mutate {\n" +
                "\t\t\tadd_field => {\n" +
                "\t\t\t  \"logTypeName\" => \"memcached_log_format1\"\n" +
                "\t\t\t}\n" +
                "\t\t\tremove_field => [\n" +
                "\t\t\t  \"grokparsefailure\"\n" +
                "\t\t\t]\n" +
                "\t\t  }\n" +
                "\t\t  ruby {\n" +
                "\t\t\tcode => \"\n" +
                "\t\t\t  event.set('[normalFields][socket_descriptor]', event.get('normalFields.socket_descriptor').to_f)\n" +
                "\t\t\t  event.remove('normalFields.socket_descriptor')\n" +
                "\t\t\t\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t  }\n" +
                "\n" +
                "\t  if [grokparsefailure] == 0 {\n" +
                "\t\tmutate {\n" +
                "\t\t  remove_field => [\n" +
                "\t\t\t\"grokparsefailure\"\n" +
                "\t\t  ]\n" +
                "\t\t}\n" +
                "\t\tgrok {\n" +
                "\t\t  match => {\n" +
                "\t\t\t\"normalFields.message\" => \"^(?<normalFields.direction><|>) %{DATA:normalFields.error.message}$\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t\truby {\n" +
                "\t\t  code => \"\n" +
                "\t\t\tevent.set('grokparsefailure', coalesce(event, 0,'normalFields.error.message'))\n" +
                "\t\t  \"\n" +
                "\t\t}\n" +
                "\t\tif !([grokparsefailure] == 0) {\n" +
                "\t\t  mutate {\n" +
                "\t\t\tadd_field => {\n" +
                "\t\t\t  \"logTypeName\" => \"memcached_log_format3\"\n" +
                "\t\t\t}\n" +
                "\t\t\tremove_field => [\n" +
                "\t\t\t  \"grokparsefailure\"\n" +
                "\t\t\t]\n" +
                "\t\t  }\n" +
                "\t\t  ruby {\n" +
                "\t\t\tcode => \"\n" +
                "\t\t\t  event.set('normalFields.direction', coalesce(event, 0, 'normalFields.direction'))\n" +
                "\t\t\t  if event.get('normalFields.direction') == 0\n" +
                "\t\t\t\tevent.remove('normalFields.direction')\n" +
                "\t\t\t   elsif(\n" +
                "\t\t\t\tevent.set('[normalFields][direction]',event.get('normalFields.direction'))\n" +
                "\t\t\t\tevent.remove('normalFields.direction')\n" +
                "\t\t\t\t)\n" +
                "\t\t\t  end\n" +
                "\t\t\t  \n" +
                "\t\t\t  event.set('[normalFields][error_message]', event.get('normalFields.error.message'))\n" +
                "\t\t\t  event.remove('normalFields.error.message')\n" +
                "\t\t\t\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t  }\n" +
                "\t\t\n" +
                "\t  if [grokparsefailure] == 0 {\n" +
                "\t\tmutate {\n" +
                "\t\t  remove_field => [\n" +
                "\t\t\t\"grokparsefailure\"\n" +
                "\t\t  ]\n" +
                "\t\t}\n" +
                "\t\tgrok {\n" +
                "\t\t  match => {\n" +
                "\t\t\t\"normalFields.message\" => \"^slab class *%{NUMBER:normalFields.socket_descriptor}:.*$\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t\truby {\n" +
                "\t\t  code => \"\n" +
                "\t\t\tevent.set('grokparsefailure', coalesce(event, 0,'normalFields.socket_descriptor'))\n" +
                "\t\t  \"\n" +
                "\t\t}\n" +
                "\t\tif !([grokparsefailure] == 0) {\n" +
                "\t\t  mutate {\n" +
                "\t\t\tadd_field => {\n" +
                "\t\t\t  \"logTypeName\" => \"memcached_log_format1\"\n" +
                "\t\t\t}\n" +
                "\t\t\tremove_field => [\n" +
                "\t\t\t  \"grokparsefailure\"\n" +
                "\t\t\t]\n" +
                "\t\t  }\n" +
                "\t\t  ruby {\n" +
                "\t\t\tcode => \"\n" +
                "\t\t\t  event.set('[normalFields][socket_descriptor]', event.get('normalFields.socket_descriptor').to_f)\n" +
                "\t\t\t  event.remove('normalFields.socket_descriptor')\n" +
                "\t\t\t\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t  }\n" +
                "\n" +
                "\t  if [grokparsefailure] == 0 {\n" +
                "\t\tmutate {\n" +
                "\t\t  remove_field => [\n" +
                "\t\t\t\"grokparsefailure\"\n" +
                "\t\t  ]\n" +
                "\t\t}\n" +
                "\t\tgrok {\n" +
                "\t\t  match => {\n" +
                "\t\t\t\"normalFields.message\" => \"^%{DATA:normalFields.error.message}$\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t\truby {\n" +
                "\t\t  code => \"\n" +
                "\t\t\t  event.set('grokparsefailure', coalesce(event, 0, 'normalFields.error.message'))\n" +
                "\t\t  \"\n" +
                "\t\t}\n" +
                "\t\tif !([grokparsefailure] == 0) {\n" +
                "\t\t  mutate {\n" +
                "\t\t\tadd_field => {\n" +
                "\t\t\t  \"logTypeName\" => \"memcached_log_format2\"\n" +
                "\t\t\t}\n" +
                "\t\t\tremove_field => [\n" +
                "\t\t\t  \"grokparsefailure\"\n" +
                "\t\t\t]\n" +
                "\t\t  }\n" +
                "\t\t  ruby {\n" +
                "\t\t\tcode => \"\n" +
                "\t\t\t  event.set('[normalFields][error_message]', event.get('normalFields.error.message'))\n" +
                "\t\t\t  event.remove('normalFields.error.message')\n" +
                "\t\t\t\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "\t  }\n" +
                "\t  mutate {\n" +
                "\t\tremove_field => [\n" +
                "\t\t  \"grokparsefailure\"\n" +
                "\t\t]\n" +
                "\t  }\n" +
                "\t  \n" +
                "\t  mutate {\n" +
                "\t\tremove_field =>[\n" +
                "\t\t   \"normalFields.message\"\n" +
                "\t\t]\n" +
                "\t  }\n" +
                "  }else{\n" +
                "\t  drop{}\n" +
                "  }\n" +
                "}";
        String templateId = "1";
        String s = uniqueFilterCode(filterStr, templateId);
        System.out.println(s);
    }

    public static String uniqueFilterCode(String filterStr, String bdTemplateid) {
        String id = "id=>\"";
        filterStr = filterStr.replaceAll(id, id + bdTemplateid + "_");
        if (filterStr.contains("ruby") && filterStr.contains("init") && filterStr.contains("class")) {
            String[] str = filterStr.split("\n");

            for(int j = 0; j < str.length; ++j) {
                String s = str[j];
                if (s.contains(" class ")) {
                    String[] str2 = s.split(" ");
                    String handleName = null;

                    for(int i = 0; i < str2.length; ++i) {
                        if (str2[i].contains("class")) {
                            handleName = str2[i + 1].trim();
                            break;
                        }
                    }

                    if (handleName != null) {
                        filterStr = filterStr.replaceAll(handleName, handleName + "_" + bdTemplateid + "_" + j);
                    }
                }
            }

            return filterStr;
        } else {
            return filterStr;
        }
    }
}
