using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using NuGet.Protocol.Plugins;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    // A FUNCIONAR 100%
    [Route("api/[controller]")]
    [ApiController]
    public class RoomController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public RoomController(SensorDbContext context) => _context = context;


        /// <summary>
        /// This method search in the database all the Rooms.
        /// </summary>
        /// <returns>List with all the Rooms</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Room>>> GetRooms() =>
            // Return an List with all the Rooms
            await _context.Rooms.ToListAsync();

        /// <summary>
        /// This method creates a new Room
        /// </summary>
        /// <param name="room"></param>
        /// <returns></returns>
        [HttpPost]
        public async Task<ActionResult<Room>> PostHistory(Room room)
        {
            // Verify if the room receibed is not null
            if (room is null) return BadRequest();

            // Add the room to an entity entry to insert into the database
            _context.Rooms.Add(room);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last Room Inserted
            return await _context.Rooms.OrderByDescending(r => r.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates an Room
        /// </summary>
        /// <param name="id">Room's Id</param>
        /// <param name="room">Room Object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutRoom(int id, Room room)
        {
            // Verify if the room receibed is not null or if the room id are the same
            if (room is null || id != room.Id)
                return BadRequest();

            // Put the room as an entry an set the state as modified to update the database
            _context.Entry(room).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes an Room from the database
        /// </summary>
        /// <param name="id">Room's Id</param>
        /// <returns>Method Result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteRoom(int id)
        {
            // Verify if the are any Rooms in the database, if not then throw exception
            if (_context.Rooms.IsNullOrEmpty())
                return BadRequest();

            // Get the room by the id
            Room? room = await _context.Rooms.FindAsync(id);

            // Send the room to the method DeleteRoom 
            return await DeleteRoom(room);
        }

        /// <summary>
        /// This method removes an Room from the database
        /// </summary>
        /// <param name="room">Room Object</param>
        /// <returns>Method Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteRoom(Room? room)
        {
            // Verify if the are any Rooms in the database or if the room is null, if not then return NotFound
            if (_context.Rooms.IsNullOrEmpty() || room is null)
                return BadRequest();

            // Put the room as an entry an set the state as remove from database
            _context.Rooms.Remove(room);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

    }
}
