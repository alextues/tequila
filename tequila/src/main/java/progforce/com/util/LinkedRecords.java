package progforce.com.util;

import progforce.com.model.domain.ChannelsTable;

import java.util.List;

public class LinkedRecords {
    // Подготовить список полей из таблицы ChannelsTable
    public static String prepareChannelsList(List<ChannelsTable> channelsTableList) {
        if(channelsTableList.isEmpty()) return "Nothing to display";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < channelsTableList.size(); i++) {
            sb.append(String.format("channelId    = %d [userId = %d, title = %s]%n",
                    channelsTableList.get(i).getChannelId(),
                    channelsTableList.get(i).getUserId(),
                    channelsTableList.get(i).getChannelName()));
        }
        return sb.toString();
    }
}
