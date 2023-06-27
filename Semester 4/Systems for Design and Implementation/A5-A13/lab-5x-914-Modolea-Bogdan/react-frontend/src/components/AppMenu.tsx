import { Box, AppBar, Toolbar, Typography, Button, IconButton } from '@mui/material';
import { Link, useLocation } from 'react-router-dom';
import HouseIcon from '@mui/icons-material/House';

function AppMenu() {
    const location = useLocation();
    const path = location.pathname;
    
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx={{ marginBottom: "20px" }}>
                <Toolbar>
                    <IconButton
                        component={Link}
                        to="/"
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="house"
                        sx={{ mr: 2 }}>
                        <HouseIcon />
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{ mr: 5 }}>
                        League Management
                    </Typography>
                    <Button 
                        variant={path.startsWith("/leagues") ? "outlined" : "text"}
                        to="/leagues"
                        component={Link}
                        color="inherit"
                        sx={{mr: 5}}>
                        Leagues
                    </Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default AppMenu;
