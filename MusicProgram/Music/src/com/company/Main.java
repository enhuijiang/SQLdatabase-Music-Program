package com.company;

import com.company.model.Artist;
import com.company.model.Datasource;
import com.company.model.SongArtist;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Datasource datasource=new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open Database");
            return;
        }
        List<Artist> artists=datasource.queryArtists(Datasource.ORDER_BY_NONE);
        if(artists==null){
            System.out.println("No artists!");
            return;
        }
        for (Artist artist:artists){
            System.out.println("ID= "+artist.getId() +", Name= "+ artist.getName());
        }
        List<String> albumsForArtist=
                datasource.queryAlbumsForArtist("Carole King",Datasource.ORDER_BY_DESC);
        for(String album: albumsForArtist){
            System.out.println(album);
        }

        List<SongArtist> songArtists=datasource.queryArtistsForSong("Heartless",Datasource.ORDER_BY_ASC);
        if(songArtists==null){
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for(SongArtist artist: songArtists){
            System.out.println("Artist name = "+ artist.getArtistName() +"\n"+
                    "Album name= "+ artist.getAlbumName()+"\n"+
                    "Track = "+ artist.getTrack());
        }
        datasource.querySongsMetadata();

        int count=datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number if songs is "+count);

        datasource.createViewForSongArtists();

//        Scanner scanner=new Scanner(System.in);
//        System.out.println("Enter a song title: ");
//        String title =scanner.nextLine();
//        songArtists =datasource.querySongInfoView(title);
//
//        if(songArtists.isEmpty()){
//            System.out.println("Couldn't find the artist for the sing");
//            return;
//        }
//        for(SongArtist artist:songArtists){
//            System.out.println("FROM VIEW ---- "+
//                    "Artist name= "+ artist.getArtistName() +", "+
//                    "Album name = "+ artist.getAlbumName()+ ", "+
//                    "Track Number = "+ artist.getTrack());
//        }

        datasource.insertSong("Bird Dog", "Everly Brothers", "All-Time Greatest Hits",7);
        datasource.close();

        //without use prepareStatement : SELECT name,album,track FROM artist_list WHERE title = "Go Your Own Way" or 1=1 or ""
        //use prepareStatement : SELECT name,album,track FROM artist_list WHERE title = "Go Your Own Way or 1=1 or ""

        // SELECT name,album,track FROM artist_list WHERE title =? OR artist= ?



    }
}
