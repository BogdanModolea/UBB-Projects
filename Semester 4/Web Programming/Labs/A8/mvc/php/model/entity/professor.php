<?php

class Professor implements JsonSerializable {
    private $id;
    private $firstName;
    private $username;
    private $password;

    public function __construct($id, $firstName, $username, $password) {
        $this->id = $id;
        $this->firstName = $firstName;
        $this->username = $username;
        $this->password = $password;
    }

    //public function __construct(){}

    public function getId()
    {
        return $this->id;
    }
    public function setId($id)
    {
        $this->id = $id;
    }
    public function getFirstName()
    {
        return $this->firstName;
    }
    public function setFirstName($firstName)
    {
        $this->firstName = $firstName;
    }
    public function getUsername()
    {
        return $this->username;
    }
    public function setUsername($username)
    {
        $this->username = $username;
    }
    public function getPassword()
    {
        return $this->password;
    }
    public function setPassword($password)
    {
        $this->password = $password;
    }

    public function jsonSerialize() {
        $vars = get_object_vars($this);
        return $vars;
    }
}

?>
