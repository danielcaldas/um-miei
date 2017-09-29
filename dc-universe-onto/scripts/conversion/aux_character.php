<?php

  class Character {
    public $id;
    public $alignment_id;
    public $city_id;
    public $thumbnail;
    public $first_ap_id;
    public $powers_ids;
    public $related_ids;
    public $alterego;
    public $facebook;
    public $history;
    public $name;
    public $occupation;
    public $real_name;
    public $short_description;
    // Relationship degree
    public $relations_class;

    public function __construct($char_id, $char_alignment, $char_city, $char_thumbnail, $char_firstap, $char_powers, $char_related, $char_alterego, $char_facebook, $char_history, $char_name, $char_occupation, $char_real_name, $char_short_description) {
      $this->id = $char_id;
      $this->alignment_id = $char_alignment;
      $this->city_id = $char_city;
      $this->thumbnail = $char_thumbnail;
      $this->first_ap_id = $char_firstap;
      $this->powers_ids = $char_powers;
      $this->related_ids = $char_related;
      $this->alterego = $char_alterego;
      $this->facebook = $char_facebook;
      $this->history = $char_history;
      $this->name = $char_name;
      $this->occupation = $char_occupation;
      $this->real_name = $char_real_name;
      $this->short_description = $char_short_description;

      $relc = array();
      for($i=0; $i < count($char_related); $i++) {
        $relc[$char_related[$i]] = 0;
      }
      $this->relations_class = $relc;
    }

    public function getRelationsString() {
      arsort($this->relations_class);
      $r = "$this->name: ";
      foreach ($this->relations_class as $ckey => $degree) {
        $r .= "[".$ckey."=".$degree."]";
      }
      return $r;
    }
  }

 ?>
