using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace DIYHomeAutomationAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class NotificationController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public NotificationController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Notifications by userId 
        /// </summary>
        /// <param name="userId">User's Id</param>
        /// <returns>List with all the notifications</returns>
        [HttpGet("{userId}")]
        public async Task<ActionResult<IEnumerable<Notification>>> GetNotifications(string userId) =>
            await (
                 from r in _context.Rooms
                 join d in _context.Devices on r.Id equals d.RoomId
                 join n in _context.Notifications on d.Id equals n.DeviceId
                 where r.UserId == userId
                 select n
            ).Union(
                from r in _context.Rooms
                join d in _context.Devices on r.Id equals d.RoomId
                join t in _context.TaskDevices on d.Id equals t.DeviceId
                join n in _context.Notifications on t.TaskId equals n.TaskId
                where r.UserId == userId
                select n
            ).Distinct().OrderByDescending(n => n.Time).ToListAsync();

        /// <summary>
        /// This method creates a new Notification
        /// </summary>
        /// <param name="notification">Notification object</param>
        /// <returns>Method Result</returns>
        [AllowAnonymous]
        [HttpPost]
        public async Task<ActionResult> PostNotification(Notification? notification)
        {
            // Verify if the notification receibed is not null
            if (notification is null) 
                return BadRequest();
            
            // Add the notification to an entity entry to insert into the database
            _context.Notifications.Add(notification);
            
            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then return an OK
            return Ok();
        }
        
        /// <summary>
        /// This method updates an Notifications
        /// </summary>
        /// <param name="id">Notification Id</param>
        /// <param name="notification">Notification object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutNotification(int id, Notification? notification)
        {
            // Verify if the notification is not null or if the notification id are the same
            if (notification is null || id != notification.Id)
                return BadRequest();

            // Put the notification as an entry an set the state as modified to update the database
            _context.Entry(notification).State = EntityState.Modified;

            // Try to save the database
            await _context.SaveChangesAsync();

            // If is successufully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes an Notification from the database 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteNotification(int id)
        {
            // Verify if the are any Notifications in the database, if not then return NotFound
            if (_context.Notifications.IsNullOrEmpty())
                return NotFound();

            // Get the notification by the id
            Notification? notification = await _context.Notifications.FindAsync(id);

            // Send the notification to the method DeleteNotification
            return await DeleteNotification(notification);
        }

        /// <summary>
        /// This method removes an Notification from the database
        /// </summary>
        /// <param name="notification">notification object</param>
        /// <returns>Method result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteNotification(Notification? notification)
        {
            // Verify if the are any Notifications in the database or if the Notification is null, if not then return NotFound 
            if (_context.Notifications.IsNullOrEmpty() || notification is null)
                return NotFound();

            // Put the Notification as an entry an set the state as remove from database
            _context.Notifications.Remove(notification);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }
    }
}
