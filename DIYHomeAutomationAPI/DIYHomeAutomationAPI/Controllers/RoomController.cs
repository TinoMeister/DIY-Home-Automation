using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RoomController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public RoomController(SensorDbContext context) => _context = context;


        [HttpGet]
        public async Task<ActionResult<IEnumerable<Room>>> GetRooms() =>
            await _context.Rooms.ToListAsync();


        [HttpPost]
        public async Task<ActionResult> PostHistory(Room? room)
        {
            if (room is null) return BadRequest();

            _context.Rooms.Add(room);

            await _context.SaveChangesAsync();

            return Ok();
        }
    }
}
